package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.da.TeachersDALayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access слой для job_table.
 */
@Component
@Profile("JDBC_TEMPLATE")
public class TeachersDA implements TeachersDALayer {

    /**
     * Запрос для поиска всех активированных записей.
     */
    public static final String QUERY_AVAILABLE = "SELECT * FROM job_type WHERE enabled = true";

    /**
     * Запрос для поиска всех активированных записей по списку id.
     */
    public static final String QUERY_AVAILABLE_BY_IDS = "SELECT * FROM job_type WHERE id IN (:ids)";

    /**
     * Запрос для поиска всех активированных записей по списку id.
     */
    public static final String QUERY_AVAILABLE_BY_ID = "SELECT * FROM job_type WHERE enabled = true AND id = ?";

    /**
     * Запрос для связки заявки и услуги.
     */
    public static final String QUERY_FOR_LINK = "INSERT INTO jobs_link (request_id, job_id) VALUES(?, ?)";

    /**
     * Запрос поиска типов услуг привязанных к заявке.
     */
    public static final String QUERY_TYPE_BY_LINK_REQUEST_ID = "SELECT t.* FROM jobs_link l LEFT JOIN job_type t ON l.job_id = t.id WHERE l.request_id = ?";

    /**
     * Зависимость на шаблон jdbc.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Зависимость на шаблон jdbc named.
     */
    @Autowired
    private NamedParameterJdbcTemplate jdbcNamed;


    /**
     * Связываем заявку и услугу.
     *
     * @param requestId
     * @param jobId
     */
    @Override
    public void linkRequest(final long requestId, final long jobId) {
        jdbcTemplate.update(QUERY_FOR_LINK, requestId, jobId);

    }

    /**
     * Получаем услугу по id.
     *
     * @param id
     * @return job
     */
    @Override
    public Teacher getById(final Long id) {
        return jdbcTemplate.query(QUERY_AVAILABLE_BY_ID, this::singleRowMapper, id);
    }

    /**
     * Получаем список услуг по списку id.
     *
     * @param ids
     * @return list job
     */
    @Override
    public List<Teacher> getByIds(final List<Long> ids) {
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        return jdbcNamed.query(QUERY_AVAILABLE_BY_IDS, map, this::rowMapper);
    }

    /**
     * Получаем список доступных услуг.
     *
     * @return list job
     */
    @Override
    public List<Teacher> getAvailable() {
        return jdbcTemplate.query(QUERY_AVAILABLE, this::rowMapper);
    }

    /**
     * Получаем список услуг связанных с заявкой.
     *
     * @param requestId
     * @return list
     */
    @Override
    public List<Teacher> getLinksByRequestId(final long requestId) {
        return jdbcTemplate.query(QUERY_TYPE_BY_LINK_REQUEST_ID, this::rowMapper, requestId);
    }

    @SneakyThrows
    private Teacher rowMapper(final ResultSet resultSet, final int pos) {
        return mapRow(resultSet);
    }

    @SneakyThrows
    private Teacher singleRowMapper(final ResultSet resultSet) {
        resultSet.next();
        return mapRow(resultSet);
    }

    private Teacher mapRow(final ResultSet resultSet) throws SQLException {
        return Teacher.builder()
                .id(resultSet.getLong("id"))
                .teacherName(resultSet.getString("title"))
                .course(resultSet.getString("desc"))
                .build();
    }

}
