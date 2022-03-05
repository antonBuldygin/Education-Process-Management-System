package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.da.SolutionDALayer;
import ru.edu.project.backend.model.SolutionStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.edu.project.backend.da.jdbctemplate.Common.getInteger;

/**
 * Data Access layer for solution.
 */
@Service
@Profile("JDBC_TEMPLATE")
public class SolutionDA implements SolutionDALayer {

    /**
     * Query for solutions by studentId.
     */
    public static final String QUERY_SOLUTIONS_BY_STUDENT_ID = "SELECT * FROM solution WHERE student_id = ?";

    /**
     * Query for solutions by studentId and taskId.
     */
    public static final String QUERY_SOLUTION_BY_STUDENT_AND_TASK = "SELECT * FROM solution WHERE student_id = ? and task_id = ?";

    /**
     * Query for solutions by taskId.
     */
    public static final String QUERY_SOLUTION_BY_TASK = "SELECT * FROM solution WHERE task_id = ?";


    /**
     * Query for finding solution by its id.
     */
    public static final String QUERY_SOLUTION_BY_ID = "SELECT * FROM solution WHERE id = ?";

    /**
     * Updating solution.
     */
    public static final String QUERY_FOR_UPDATE = "UPDATE solution SET text = :text, status = :status, score = :score, creation_time = :creation_time, last_action_time = :last_action_time, checked_time = :checked_time WHERE id = :id";


    /**
     * Query for linking solution and action.
     */
    public static final String QUERY_FOR_LINK = "INSERT INTO solution_action (solution_id, action_type_id, action_time, comment) VALUES(?, ?, ?, ?)";

    /**
     * Query for updating linking solution and action.
     */
    public static final String QUERY_FOR_UPDATE_LINK = "UPDATE solution_action SET action_time = :action_time, comment = :comment WHERE solution_id = :solution_id and action_type_id = :action_type_id";


    /**
     * Query for finding actions by solutionId.
     */
    public static final String QUERY_ACTIONS_BY_SOLUTION = "SELECT * FROM solution_action WHERE solution_id = ?";


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

    /**
     * Embedding a jdbc insert dependency with a table setting.
     *
     * @param bean
     */
    @Autowired
    public void setJdbcInsert(final SimpleJdbcInsert bean) {
        jdbcInsert = bean
                .withTableName("solution")
                .usingGeneratedKeyColumns("id");

    }



    /**
     * Getting solutions by student id.
     *
     * @param studentId
     * @return
     */
    @Override
    public List<SolutionInfo> getSolutionsByStudent(final long studentId) {
        return jdbcTemplate.query(QUERY_SOLUTIONS_BY_STUDENT_ID, this::solutionRowMapper, studentId);
    }

    /**
     * Searching for solutions.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    public PagedView<SolutionInfo> search(final SolutionSearch recordSearch) {
        return null;
    }

    /**
     * Getting student's solution by task id.
     *
     * @param studentId
     * @param taskId
     * @return list of solutions
     */
    @Override
    public SolutionInfo getSolutionByStudentAndTask(final long studentId, final long taskId) {
        return jdbcTemplate.query(QUERY_SOLUTION_BY_STUDENT_AND_TASK, this::singleSolutionRowMapper, studentId, taskId);
    }

    /**
     * Getting solutions by task id.
     *
     * @param taskId
     * @return list of solutions
     */
    @Override
    public List<SolutionInfo> getSolutionsByTask(final long taskId) {
        return jdbcTemplate.query(QUERY_SOLUTION_BY_TASK, this::solutionRowMapper, taskId);
    }

    /** Getting solution by id.
     *
     * @param id
     * @return
     */
    @Override
    public SolutionInfo getById(final long id) {
        return jdbcTemplate.query(QUERY_SOLUTION_BY_ID, this::singleSolutionRowMapper, id);
    }

    /**
     * Inserting/updating solution.
     *
     * @param solutionInfo
     */
    @Override
    public SolutionInfo save(final SolutionInfo solutionInfo) {
        if (solutionInfo.getId() == null) {
            return insert(solutionInfo);
        }
        return update(solutionInfo);
    }

    /**
     * Create solution action link.
     *
     * @param solutionInfo
     */
    @Override
    public void doAction(final SolutionInfo solutionInfo, final String comment) {
        jdbcTemplate.update(QUERY_FOR_LINK,
                solutionInfo.getId(),
                solutionInfo.getStatus().getCode(),
                solutionInfo.getLastActionTime(),
                comment);
    }

    /**
     * Getting list of actions by solution id.
     *
     * @param solutionId
     * @return List<Action>
     */
    @Override
    public List<Action> getActionsBySolution(final long solutionId) {
        return jdbcTemplate.query(QUERY_ACTIONS_BY_SOLUTION, this::actionRowMapper, solutionId);
    }

    /**
     * Update solution action link.
     *
     * @param solutionInfo
     * @param comment
     */
    @Override
    public void updateAction(final SolutionInfo solutionInfo, final String comment) {
        jdbcNamed.update(QUERY_FOR_UPDATE_LINK, linkToMap(solutionInfo, comment));
    }


    private SolutionInfo update(final SolutionInfo draft) {
        jdbcNamed.update(QUERY_FOR_UPDATE, solutionToMap(draft));
        return draft;
    }


    private SolutionInfo insert(final SolutionInfo draft) {
        long id = jdbcInsert.executeAndReturnKey(solutionToMap(draft)).longValue();
        draft.setId(id);
        return draft;
    }

    private Map<String, Object> solutionToMap(final SolutionInfo draft) {
        HashMap<String, Object> map = new HashMap<>();
        if (draft.getId() != null) {
            map.put("id", draft.getId());
        }

        map.put("task_id", draft.getTaskId());
        map.put("student_id", draft.getStudentId());
        map.put("text", draft.getText());
        map.put("status", draft.getStatus().getCode());
        map.put("score", draft.getScore().getValue());
        map.put("creation_time", draft.getCreationTime());
        map.put("last_action_time", draft.getLastActionTime());
        map.put("checked_time", draft.getCheckedTime());

        return map;
    }

    private Map<String, Object> linkToMap(final SolutionInfo draft, final String comment) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("solution_id", draft.getId());
        map.put("action_type_id", draft.getStatus().getCode());
        map.put("action_time", draft.getLastActionTime());
        map.put("comment", comment);

        return map;
    }


    @SneakyThrows
    private SolutionInfo solutionRowMapper(final ResultSet rs, final int pos) {
        return solutionMapRow(rs);
    }

    @SneakyThrows
    private SolutionInfo singleSolutionRowMapper(final ResultSet rs) {
        rs.next();
        return solutionMapRow(rs);
    }



    private SolutionInfo solutionMapRow(final ResultSet rs) throws SQLException {

        return SolutionInfo.builder()
                .id(rs.getLong("id"))
                .studentId(rs.getLong("student_id"))
                .taskId(rs.getLong("task_id"))
                .status(SolutionStatus.byCode(rs.getLong("status")))
                .creationTime(rs.getTimestamp("creation_time"))
                .lastActionTime(rs.getTimestamp("last_action_time"))
                .checkedTime(rs.getTimestamp("checked_time"))
                .score(new Score(getInteger(rs, "score")))
                .text(rs.getString("text"))
                .build();
    }

    @SneakyThrows
    private Action actionRowMapper(final ResultSet rs, final int pos) {
        return actionMapRow(rs);
    }

    private Action actionMapRow(final ResultSet rs) throws SQLException {
        return Action.builder()
                .status(SolutionStatus.byCode(rs.getLong("action_type_id")))
                .comment(rs.getString("comment"))
                .time(rs.getTimestamp("action_time"))
                .build();
    }
}
