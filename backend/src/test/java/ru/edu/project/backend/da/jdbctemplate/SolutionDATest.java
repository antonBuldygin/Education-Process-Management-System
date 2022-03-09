package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.model.SolutionStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static ru.edu.project.backend.da.jdbctemplate.SolutionDA.*;


public class SolutionDATest {

    public static final Long SOLUTION_ID = 111L;
    public static final Long STUDENT_ID = 222L;
    public static final Long TASK_ID = 333L;
    public static final Long SOLUTION_STATUS = 1L;
    public static final Timestamp SOLUTION_CREATION_TS = new Timestamp(1L);
    public static final Timestamp SOLUTION_LAST_ACTION_TS = new Timestamp(2L);
    public static final Timestamp SOLUTION_CHECKED_TS = new Timestamp(3L);
    public static final Integer SOLUTION_SCORE = 56;
    public static final String SOLUTION_TEXT = "Text of the solution";
    public static final String SOLUTION_COMMENT = "Comment of the solution";
    public static final Long ACTION_TYPE_ID = 2L;
    public static final String ACTION_COMMENT = "Action comment";
    public static final Timestamp ACTION_CREATION_TS = new Timestamp(4L);


    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    NamedParameterJdbcTemplate jdbcNamed;


    SimpleJdbcInsert jdbcInsert;

    @InjectMocks
    private SolutionDA da;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
        jdbcInsert = mock(SimpleJdbcInsert.class);
        when(jdbcInsert.withTableName(any())).thenReturn(jdbcInsert);
        when(jdbcInsert.usingGeneratedKeyColumns(any())).thenReturn(jdbcInsert);
        da.setJdbcInsert(jdbcInsert);
    }

    @Test
    @SneakyThrows
    public void getSolutionsByStudent() {

        List<SolutionInfo> expectedResult = new ArrayList();

        ResultSet resultSetMock = configResultSolutionSetMock();

        when(jdbcTemplate.query(eq(QUERY_SOLUTIONS_BY_STUDENT_ID), any(RowMapper.class), eq(STUDENT_ID))).thenAnswer(invocation -> {
            RowMapper<SolutionInfo> rowMapper = invocation.getArgument(1, RowMapper.class);
            expectedResult.add(rowMapper.mapRow(resultSetMock, 1));
            return expectedResult;
        });

        List<SolutionInfo> list = da.getSolutionsByStudent(STUDENT_ID);

        verify(jdbcTemplate).query(eq(QUERY_SOLUTIONS_BY_STUDENT_ID), any(RowMapper.class), eq(STUDENT_ID));

        assertEquals(1, list.size());

        SolutionInfo info = list.get(0);

        assertEqualsForSolution(info);
    }


    @Test
    @SneakyThrows
    public void getSolutionByStudentAndTask() {

        ResultSet resultSetMock = configResultSolutionSetMock();

        when(jdbcTemplate.query(eq(QUERY_SOLUTION_BY_STUDENT_AND_TASK), any(ResultSetExtractor.class), eq(STUDENT_ID), eq(TASK_ID))).thenAnswer(invocation -> {
            ResultSetExtractor<SolutionInfo> resultSetExtractor = invocation.getArgument(1, ResultSetExtractor.class);
            return resultSetExtractor.extractData(resultSetMock);
        });

        SolutionInfo info = da.getSolutionByStudentAndTask(STUDENT_ID, TASK_ID);

        assertNotNull(info);

        verify(jdbcTemplate).query(eq(QUERY_SOLUTION_BY_STUDENT_AND_TASK), any(ResultSetExtractor.class),eq(STUDENT_ID), eq(TASK_ID));

        assertEqualsForSolution(info);
    }

    @Test
    @SneakyThrows
    public void getSolutionsByTask() {

        List<SolutionInfo> expectedResult = new ArrayList();

        ResultSet resultSetMock = configResultSolutionSetMock();

        when(jdbcTemplate.query(eq(QUERY_SOLUTION_BY_TASK), any(RowMapper.class), eq(TASK_ID))).thenAnswer(invocation -> {
            RowMapper<SolutionInfo> rowMapper = invocation.getArgument(1, RowMapper.class);
            expectedResult.add(rowMapper.mapRow(resultSetMock, 1));
            return expectedResult;
        });

        List<SolutionInfo> list = da.getSolutionsByTask(TASK_ID);

        verify(jdbcTemplate).query(eq(QUERY_SOLUTION_BY_TASK), any(RowMapper.class), eq(TASK_ID));

        assertEquals(1, list.size());

        SolutionInfo info = list.get(0);
        assertEqualsForSolution(info);
    }

    @Test
    @SneakyThrows
    public void getById() {

        ResultSet resultSetMock = configResultSolutionSetMock();

        when(jdbcTemplate.query(eq(QUERY_SOLUTION_BY_ID), any(ResultSetExtractor.class), eq(SOLUTION_ID))).thenAnswer(invocation -> {
            ResultSetExtractor<SolutionInfo> resultSetExtractor = invocation.getArgument(1, ResultSetExtractor.class);
            return resultSetExtractor.extractData(resultSetMock);
        });

        SolutionInfo info = da.getById(SOLUTION_ID);

        assertNotNull(info);

        verify(jdbcTemplate).query(eq(QUERY_SOLUTION_BY_ID), any(ResultSetExtractor.class),eq(SOLUTION_ID));

        assertEqualsForSolution(info);
    }

    @Test
    @SneakyThrows
    public void saveUpdate() {

        SolutionInfo solution = SolutionInfo.builder()
                .id(SOLUTION_ID)
                .taskId(TASK_ID)
                .studentId(STUDENT_ID)
                .status(SolutionStatus.byCode(SOLUTION_STATUS))
                .text(SOLUTION_TEXT)
                .score(new Score(SOLUTION_SCORE))
                .creationTime(SOLUTION_CREATION_TS)
                .lastActionTime(SOLUTION_LAST_ACTION_TS)
                .checkedTime(SOLUTION_CHECKED_TS)
                .build();

        SolutionInfo result = da.save(solution);

        verify(jdbcNamed).update(eq(QUERY_FOR_UPDATE), anyMap());

        assertEquals(solution.getId(), result.getId());

    }

    @Test
    @SneakyThrows
    public void saveInsert() {

        SolutionInfo solution = SolutionInfo.builder()
                .taskId(TASK_ID)
                .studentId(STUDENT_ID)
                .status(SolutionStatus.byCode(SOLUTION_STATUS))
                .text(SOLUTION_TEXT)
                .score(new Score(SOLUTION_SCORE))
                .creationTime(SOLUTION_CREATION_TS)
                .lastActionTime(SOLUTION_LAST_ACTION_TS)
                .checkedTime(SOLUTION_CHECKED_TS)
                .build();

        when(jdbcInsert.executeAndReturnKey(anyMap())).thenReturn(SOLUTION_ID);

        SolutionInfo result = da.save(solution);

        assertEquals(SOLUTION_ID, result.getId());

        verify(jdbcInsert).executeAndReturnKey(anyMap());

    }

    @Test
    @SneakyThrows
    public void doAction() {
        SolutionInfo solution = SolutionInfo.builder()
                .id(SOLUTION_ID)
                .status(SolutionStatus.byCode(SOLUTION_STATUS))
                .lastActionTime(SOLUTION_LAST_ACTION_TS)
                .build();

        da.doAction(solution, SOLUTION_COMMENT);

        verify(jdbcTemplate).update(eq(QUERY_FOR_LINK),
                eq(SOLUTION_ID),
                eq(SOLUTION_STATUS),
                eq(SOLUTION_LAST_ACTION_TS),
                eq(SOLUTION_COMMENT));
    }

    @Test
    @SneakyThrows
    public void getActionsBySolution() {
        List<Action> expectedResult = new ArrayList();

        ResultSet resultSetMock = configResultActionSetMock();

        when(jdbcTemplate.query(eq(QUERY_ACTIONS_BY_SOLUTION), any(RowMapper.class), eq(SOLUTION_ID))).thenAnswer(invocation -> {
            RowMapper<Action> rowMapper = invocation.getArgument(1, RowMapper.class);
            expectedResult.add(rowMapper.mapRow(resultSetMock, 1));
            return expectedResult;
        });

        List<Action> list = da.getActionsBySolution(SOLUTION_ID);

        verify(jdbcTemplate).query(eq(QUERY_ACTIONS_BY_SOLUTION), any(RowMapper.class), eq(SOLUTION_ID));

        assertEquals(1, list.size());

        Action info = list.get(0);

        assertNotNull(info);

        assertEqualsForAction(info);
    }

    @Test
    @SneakyThrows
    public void updateAction() {
        SolutionInfo solution = SolutionInfo.builder()
                .id(SOLUTION_ID)
                .status(SolutionStatus.byCode(SOLUTION_STATUS))
                .lastActionTime(SOLUTION_LAST_ACTION_TS)
                .build();

        da.updateAction(solution, SOLUTION_COMMENT);

        verify(jdbcNamed).update(eq(QUERY_FOR_UPDATE_LINK), anyMap());
    }

    private void assertEqualsForSolution(SolutionInfo info) {
        assertEquals(SOLUTION_ID, info.getId());
        assertEquals(STUDENT_ID, info.getStudentId());
        assertEquals(TASK_ID, info.getTaskId());
        assertEquals(SOLUTION_STATUS, info.getStatus().getCode());
        assertEquals(SOLUTION_CREATION_TS, info.getCreationTime());
        assertEquals(SOLUTION_LAST_ACTION_TS, info.getLastActionTime());
        assertEquals(SOLUTION_CHECKED_TS, info.getCheckedTime());
        assertEquals(SOLUTION_SCORE, info.getScore().getValue());
        assertEquals(SOLUTION_TEXT, info.getText());
    }

    private ResultSet configResultSolutionSetMock() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getLong("id")).thenReturn(SOLUTION_ID);
        when(resultSetMock.getLong("student_id")).thenReturn(STUDENT_ID);
        when(resultSetMock.getLong("task_id")).thenReturn(TASK_ID);
        when(resultSetMock.getLong("status")).thenReturn(SOLUTION_STATUS);
        when(resultSetMock.getTimestamp("creation_time")).thenReturn(SOLUTION_CREATION_TS);
        when(resultSetMock.getTimestamp("last_action_time")).thenReturn(SOLUTION_LAST_ACTION_TS);
        when(resultSetMock.getTimestamp("checked_time")).thenReturn(SOLUTION_CHECKED_TS);
        when(resultSetMock.getInt("score")).thenReturn(SOLUTION_SCORE);
        when(resultSetMock.getString("text")).thenReturn(SOLUTION_TEXT);
        return resultSetMock;
    }

    private ResultSet configResultActionSetMock() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getLong("action_type_id")).thenReturn(ACTION_TYPE_ID);
        when(resultSetMock.getString("comment")).thenReturn(ACTION_COMMENT);
        when(resultSetMock.getTimestamp("action_time")).thenReturn(ACTION_CREATION_TS);
        return resultSetMock;
    }

    private void assertEqualsForAction(Action info) {
        assertEquals(ACTION_TYPE_ID, info.getStatus().getCode());
        assertEquals(ACTION_COMMENT, info.getComment());
        assertEquals(ACTION_CREATION_TS, info.getTime());
    }

}