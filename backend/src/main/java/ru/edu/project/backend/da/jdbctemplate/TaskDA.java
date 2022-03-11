package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.da.TaskDALayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access layer for task table.
 */
@Component
@Profile("JDBC_TEMPLATE")
public class TaskDA implements TaskDALayer {

    /**
     * Getting task by id.
     */
    public static final String QUERY_TASK_BY_ID = "SELECT * FROM task WHERE id = ?";

    /**
     * Getting tasks by group id.
     */
    public static final String QUERY_TASKS_BY_GROUP_ID = "SELECT * FROM task WHERE group_id = ?";

    /**
     * Updating task.
     */
    public static final String QUERY_FOR_UPDATE = "UPDATE task SET group_id = :group_id, title = :title, text = :text, start_date = :start_date, end_date = :end_date WHERE id = :id";

    /**
     * Getting available tasks.
     */
    public static final String QUERY_AVAILABLE_TASKS = "SELECT * FROM task WHERE start_date <= ? and end_date >= ? and group_id = ?";

    /**
     * Deleting task.
     */
    public static final String QUERY_FOR_DELETE = "DELETE FROM task WHERE id = :id";


    /**
     * Dependency on the jdbc template.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Dependency on the jdbc insert template.
     */
    private SimpleJdbcInsert jdbcInsert;

    /**
     * Dependency on the jdbc named template.
     */
    @Autowired
    private NamedParameterJdbcTemplate jdbcNamed;

    @SneakyThrows
    private TaskInfo rowMapper(final ResultSet resultSet, final int pos) {
        return mapRow(resultSet);
    }

    @SneakyThrows
    private TaskInfo singleRowMapper(final ResultSet resultSet) {
        resultSet.next();
        return mapRow(resultSet);
    }

    private TaskInfo mapRow(final ResultSet resultSet) throws SQLException {
        return TaskInfo.builder()
                .id(resultSet.getLong("id"))
                .num(resultSet.getInt("num"))
                .groupId(resultSet.getLong("group_id"))
                .title(resultSet.getString("title"))
                .text(resultSet.getString("text"))
                .startDate(resultSet.getTimestamp("start_date"))
                .endDate(resultSet.getTimestamp("end_date"))
                .build();
    }

    /**
     * Getting available tasks on current date.
     *
     * @param groupId
     * @return List<TaskInfo>
     */
    @Override
    public List<TaskInfo> getAvailable(final Timestamp date, final long groupId) {
        return jdbcTemplate.query(QUERY_AVAILABLE_TASKS, this::rowMapper, date.toString(), date.toString(), groupId);
    }

    /**
     * Saving task.
     *
     * @param draft
     * @return
     */
    @Override
    public TaskInfo save(final TaskInfo draft) {
        if (draft.getId() == null) {
            return insert(draft);
        }
        return update(draft);
    }

    private TaskInfo update(final TaskInfo draft) {
        jdbcNamed.update(QUERY_FOR_UPDATE, toMap(draft));
        return draft;
    }

    /**
     * Embedding a jdbc insert dependency with a table setting.
     *
     * @param bean
     */
    @Autowired
    public void setJdbcInsert(final SimpleJdbcInsert bean) {
        jdbcInsert = bean
                .withTableName("task")
                .usingGeneratedKeyColumns("id");

    }

    private TaskInfo insert(final TaskInfo draft) {
        long id = jdbcInsert.executeAndReturnKey(toMap(draft)).longValue();
        draft.setId(id);
        return draft;
    }

    private Map<String, Object> toMap(final TaskInfo draft) {
        HashMap<String, Object> map = new HashMap<>();
        if (draft.getId() != null) {
            map.put("id", draft.getId());
        }

        map.put("num", draft.getNum());
        map.put("group_id", draft.getGroupId());
        map.put("title", draft.getTitle());
        map.put("text", draft.getText());
        map.put("start_date", draft.getStartDate());
        map.put("end_date", draft.getEndDate());

        return map;
    }

    /**
     * Getting all tasks by group id.
     *
     * @param groupId
     * @return list of tasks
     */
    @Override
    public List<TaskInfo> getTasksByGroup(final long groupId) {
        return jdbcTemplate.query(QUERY_TASKS_BY_GROUP_ID, this::rowMapper, groupId);
    }

    /**
     * Getting all tasks.
     *
     * @return list of tasks
     */
    @Override
    public List<TaskInfo> getAllTasks() {
        return null;
    }

    /**
     * Get task by id.
     *
     * @param id
     * @return список
     */
    @Override
    public TaskInfo getById(final long id) {
        return jdbcTemplate.query(QUERY_TASK_BY_ID, this::singleRowMapper, id);
    }

    /**
     * Delete task by id.
     *
     * @param id
     * @return TaskInfo
     */
    @Override
    public int deleteById(final long id) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("id", id);

        return jdbcNamed.update(QUERY_FOR_DELETE, map);
    }
}
