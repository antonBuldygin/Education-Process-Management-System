package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.da.GroupsDALayer;
import ru.edu.project.backend.model.GroupStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access слой для groups.
 */
@Service
@Profile("JDBC_TEMPLATE")
public class GroupsDA implements GroupsDALayer {


    /**
     * Запрос для поиска всех созданных групп.
     */
    public static final String QUERY_FOR_ALL_GROUPS = "SELECT * FROM group_g";

    /**
     * Запрос для удаления группы.
     */
    public static final String QUERY_TO_DELETE = "DELETE FROM group_g where id=?";

    /**
     * Запрос для поиска группы по id.
     */
    public static final String QUERY_FOR_ID = "SELECT * FROM group_g WHERE id = ?";

    /**
     * Обновление группы.
     * Обновляем только изменяемые поля.
     */
    public static final String QUERY_FOR_UPDATE = "UPDATE group_g SET status = :status, last_action_time = :last_action_time, closed_time = :closed_time, price = :price, comment = :comment WHERE id = :id";

    /**
     * Запрос поиска типов услуг привязанных к заявке.
     */
    public static final String QUERY_TYPE_BY_LINK_REQUEST_ID = "SELECT t.* FROM jobs_link l LEFT JOIN group_g t ON l.request_id = t.id WHERE l.job_id = ?";


    /**
     * Зависимость на шаблон jdbc.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Зависимость на шаблон jdbc insert.
     */
    private SimpleJdbcInsert jdbcInsert;

    /**
     * Зависимость на шаблон jdbc named.
     */
    @Autowired
    private NamedParameterJdbcTemplate jdbcNamed;

    /**
     * Внедрение зависимости jdbc insert с настройкой для таблицы.
     *
     * @param bean
     */
    @Autowired
    public void setJdbcInsert(final SimpleJdbcInsert bean) {
        jdbcInsert = bean
                .withTableName("group_g")
                .usingGeneratedKeyColumns("id");

    }


    /**
     * Получение списка заявок по clientId.
     *
     * @return list group
     */
    @Override
    public List<GroupInfo> getAllGroupsInfo() {

        return jdbcTemplate.query(QUERY_FOR_ALL_GROUPS, this::rowMapper);
    }

    /**
     * удаление группы по id.
     *
     * @param id
     */
    @Override
    public void deleteGroup(final long id) {
        jdbcTemplate.update(QUERY_TO_DELETE, id);

    }

    /**
     * Поиск заявки клиента.
     *
     * @param id
     * @return group
     */
    @Override
    public GroupInfo getById(final long id) {
        if (jdbcTemplate.query(QUERY_FOR_ID, this::singleRowMapper, id) != null) {
            return jdbcTemplate.query(QUERY_FOR_ID, this::singleRowMapper, id);
        } else {
            return null;
        }
    }

    /**
     * Сохранение заявки.
     *
     * @param draft
     * @return
     */
    @Override
    public GroupInfo save(final GroupInfo draft) {
        if (draft.getId() == null) {
            return insert(draft);
        }
        return update(draft);
    }


    /**
     * Поиск заявок.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    public PagedView<GroupInfo> search(final RecordSearch recordSearch) {
        //пока не реализуем этот метод, так как целевая реализация - Spring Data
        throw new RuntimeException("Need to implement ru.edu.project.backend.da.jdbctemplate.RequestDA.search");
    }

    /**
     * Получение заявки по teacher_id.
     *
     * @param jobId
     * @return groupInfo
     */
    @Override
    public List<GroupInfo> getByTeacherId(final long jobId) {
        return jdbcTemplate.query(QUERY_TYPE_BY_LINK_REQUEST_ID, this::rowMapper, jobId);
    }

    private GroupInfo update(final GroupInfo draft) {
        jdbcNamed.update(QUERY_FOR_UPDATE, toMap(draft));
        return draft;
    }


    private GroupInfo insert(final GroupInfo draft) {
        long id = jdbcInsert.executeAndReturnKey(toMap(draft)).longValue();
        draft.setId(id);
        return draft;
    }

    private Map<String, Object> toMap(final GroupInfo draft) {
        HashMap<String, Object> map = new HashMap<>();
        if (draft.getId() != null) {
            map.put("id", draft.getId());
        }

        map.put("client_id", draft.getId());
        map.put("status", draft.getStatus().getCode());
        map.put("created_time", draft.getCreatedAt());
//        map.put("planned_visit_time", draft.getPlannedVisitAt());
        map.put("last_action_time", draft.getLastActionAt());
        map.put("closed_time", draft.getClosedAt());
        map.put("comment", draft.getComment());

        return map;
    }


    @SneakyThrows
    private GroupInfo rowMapper(final ResultSet rs, final int pos) {
        return mapRow(rs);
    }

    @SneakyThrows
    private GroupInfo singleRowMapper(final ResultSet rs) {
        rs.next();
        return mapRow(rs);
    }

    private GroupInfo mapRow(final ResultSet rs) throws SQLException {
        return GroupInfo.builder()
                .id(rs.getLong("id"))
                .status(GroupStatus.byCode(rs.getLong("status")))
                .createdAt(rs.getTimestamp("created_time"))
//                .plannedVisitAt(rs.getTimestamp("planned_visit_time"))
                .lastActionAt(rs.getTimestamp("last_action_time"))
                .closedAt(rs.getTimestamp("closed_time"))

                .comment(rs.getString("comment"))
                .build();
    }
}
