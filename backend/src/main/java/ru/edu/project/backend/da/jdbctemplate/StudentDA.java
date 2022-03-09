package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.da.StudentDALayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access layer for student table.
 */
@Component
@Profile("JDBC_TEMPLATE")
public class StudentDA implements StudentDALayer {

    /**
     * Getting student by id.
     */
    public static final String QUERY_STUDENT_BY_ID = "SELECT * FROM student WHERE id = ?";

    /**
     * Updating student.
     */
    public static final String QUERY_FOR_UPDATE = "UPDATE student SET group_id = :group_id, first_name = :first_name, last_name = :last_name, email = :email, birthday = :birthday, phone = :phone  WHERE id = :id";

    /**
     * Getting all students.
     */
    public static final String QUERY_ALL_STUDENTS = "SELECT * FROM student";


    /**
     * Deleting student.
     */
    public static final String QUERY_FOR_DELETE = "DELETE FROM student WHERE id = :id";


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
    private StudentInfo rowMapper(final ResultSet resultSet, final int pos) {
        return mapRow(resultSet);
    }

    @SneakyThrows
    private StudentInfo singleRowMapper(final ResultSet resultSet) {
        resultSet.next();
        return mapRow(resultSet);
    }

    private StudentInfo mapRow(final ResultSet resultSet) throws SQLException {
        return StudentInfo.builder()
                .id(resultSet.getLong("id"))
                .groupId(resultSet.getLong("group_id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .phone(resultSet.getString("phone"))
                .birthday(resultSet.getTimestamp("birthday"))
                .build();
    }


    /**
     * Saving student.
     *
     * @param draft
     * @return StudentInfo
     */
    @Override
    public StudentInfo save(final StudentInfo draft) {
        if (draft.getId() == null) {
            return insert(draft);
        }
        return update(draft);
    }

    private StudentInfo update(final StudentInfo draft) {
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
                .withTableName("student")
                .usingGeneratedKeyColumns("id");

    }

    private StudentInfo insert(final StudentInfo draft) {
        long id = jdbcInsert.executeAndReturnKey(toMap(draft)).longValue();
        draft.setId(id);
        return draft;
    }

    private Map<String, Object> toMap(final StudentInfo draft) {
        HashMap<String, Object> map = new HashMap<>();
        if (draft.getId() != null) {
            map.put("id", draft.getId());
        }

        map.put("group_id", draft.getGroupId());
        map.put("first_name", draft.getFirstName());
        map.put("last_name", draft.getLastName());
        map.put("email", draft.getEmail());
        map.put("birthday", draft.getBirthday());
        map.put("phone", draft.getPhone());

        return map;
    }


    /**
     * Get student by id.
     *
     * @param id
     * @return StudentInfo
     */
    @Override
    public StudentInfo getById(final long id) {
        return jdbcTemplate.query(QUERY_STUDENT_BY_ID, this::singleRowMapper, id);
    }

    /**
     * Delete student by id.
     *
     * @param id
     * @return StudentInfo
     */
    @Override
    public int deleteById(final long id) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("id", id);

        return jdbcNamed.update(QUERY_FOR_DELETE, map);
    }

    /**
     * Getting all students.
     *
     * @return list of students
     */
    @Override
    public List<StudentInfo> getAllStudents() {
        return jdbcTemplate.query(QUERY_ALL_STUDENTS, this::rowMapper);
    }
}
