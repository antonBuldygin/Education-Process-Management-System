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
import ru.edu.project.backend.api.tasks.TaskInfo;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static ru.edu.project.backend.da.jdbctemplate.TaskDA.*;


public class TaskDATest {

    public static final Long TASK_ID = 123L;
    public static final Long GROUP_ID = 1234L;
    public static final Integer TASK_NUM = 1;
    public static final Timestamp TASK_START_DATE_TS = new Timestamp(1L);
    public static final Timestamp TASK_END_DATE_TS = new Timestamp(3L);
    public static final String TASK_TITLE = "First task";
    public static final String TASK_TEXT = "Text of the first task";

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    NamedParameterJdbcTemplate jdbcNamed;


    SimpleJdbcInsert jdbcInsert;

    @InjectMocks
    private TaskDA da;

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
    public void getTasksByGroup() {

        List<TaskInfo> expectedResult = new ArrayList();

        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getLong("id")).thenReturn(TASK_ID);
        when(resultSetMock.getLong("group_id")).thenReturn(GROUP_ID);
        when(resultSetMock.getInt("num")).thenReturn(TASK_NUM);
        when(resultSetMock.getTimestamp("start_date")).thenReturn(TASK_START_DATE_TS);
        when(resultSetMock.getTimestamp("end_date")).thenReturn(TASK_END_DATE_TS);
        when(resultSetMock.getString("title")).thenReturn(TASK_TITLE);
        when(resultSetMock.getString("text")).thenReturn(TASK_TEXT);

        when(jdbcTemplate.query(eq(QUERY_TASKS_BY_GROUP_ID), any(RowMapper.class), eq(GROUP_ID))).thenAnswer(invocation -> {
            RowMapper<TaskInfo> rowMapper = invocation.getArgument(1, RowMapper.class);
            expectedResult.add(rowMapper.mapRow(resultSetMock, 1));
            return expectedResult;
        });

        List<TaskInfo> list = da.getTasksByGroup(GROUP_ID);

        verify(jdbcTemplate).query(eq(QUERY_TASKS_BY_GROUP_ID), any(RowMapper.class), eq(GROUP_ID));

        assertEquals(1, list.size());

        TaskInfo info = list.get(0);
        assertEquals(TASK_ID, info.getId());
        assertEquals(GROUP_ID, info.getGroupId());
        assertEquals(TASK_NUM, info.getNum());
        assertEquals(TASK_START_DATE_TS, info.getStartDate());
        assertEquals(TASK_END_DATE_TS, info.getEndDate());
        assertEquals(TASK_TEXT, info.getText());
        assertEquals(TASK_TITLE, info.getTitle());
    }


    @Test
    @SneakyThrows
    public void getAvailable() {

        List<TaskInfo> expectedResult = new ArrayList();

        Timestamp currentDate = new Timestamp(2L);
        String currentDateString = currentDate.toString();

        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getLong("id")).thenReturn(TASK_ID);
        when(resultSetMock.getLong("group_id")).thenReturn(GROUP_ID);
        when(resultSetMock.getInt("num")).thenReturn(TASK_NUM);
        when(resultSetMock.getTimestamp("start_date")).thenReturn(TASK_START_DATE_TS);
        when(resultSetMock.getTimestamp("end_date")).thenReturn(TASK_END_DATE_TS);
        when(resultSetMock.getString("title")).thenReturn(TASK_TITLE);
        when(resultSetMock.getString("text")).thenReturn(TASK_TEXT);

        when(jdbcTemplate.query(eq(QUERY_AVAILABLE_TASKS), any(RowMapper.class), eq(currentDateString), eq(currentDateString), eq(GROUP_ID))).thenAnswer(invocation -> {
            RowMapper<TaskInfo> rowMapper = invocation.getArgument(1, RowMapper.class);
            expectedResult.add(rowMapper.mapRow(resultSetMock, 1));
            return expectedResult;
        });

        List<TaskInfo> list = da.getAvailable(currentDate, GROUP_ID);

        verify(jdbcTemplate).query(eq(QUERY_AVAILABLE_TASKS), any(RowMapper.class), eq(currentDateString), eq(currentDateString), eq(GROUP_ID));

        assertEquals(1, list.size());

        TaskInfo info = list.get(0);
        assertEquals(TASK_ID, info.getId());
        assertEquals(GROUP_ID, info.getGroupId());
        assertEquals(TASK_NUM, info.getNum());
        assertEquals(TASK_START_DATE_TS, info.getStartDate());
        assertEquals(TASK_END_DATE_TS, info.getEndDate());
        assertEquals(TASK_TEXT, info.getText());
        assertEquals(TASK_TITLE, info.getTitle());
    }

    @Test
    @SneakyThrows
    public void getById() {

        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getLong("id")).thenReturn(TASK_ID);
        when(resultSetMock.getLong("group_id")).thenReturn(GROUP_ID);
        when(resultSetMock.getInt("num")).thenReturn(TASK_NUM);
        when(resultSetMock.getTimestamp("start_date")).thenReturn(TASK_START_DATE_TS);
        when(resultSetMock.getTimestamp("end_date")).thenReturn(TASK_END_DATE_TS);
        when(resultSetMock.getString("title")).thenReturn(TASK_TITLE);
        when(resultSetMock.getString("text")).thenReturn(TASK_TEXT);

        when(jdbcTemplate.query(eq(QUERY_TASK_BY_ID), any(ResultSetExtractor.class), eq(TASK_ID))).thenAnswer(invocation -> {
            ResultSetExtractor<TaskInfo> resultSetExtractor = invocation.getArgument(1, ResultSetExtractor.class);
            return resultSetExtractor.extractData(resultSetMock);
        });

        TaskInfo task = da.getById(TASK_ID);

        assertNotNull(task);

        verify(jdbcTemplate).query(eq(QUERY_TASK_BY_ID), any(ResultSetExtractor.class), eq(TASK_ID));

        assertEquals(TASK_ID, task.getId());
        assertEquals(GROUP_ID, task.getGroupId());
        assertEquals(TASK_NUM, task.getNum());
        assertEquals(TASK_START_DATE_TS, task.getStartDate());
        assertEquals(TASK_END_DATE_TS, task.getEndDate());
        assertEquals(TASK_TEXT, task.getText());
        assertEquals(TASK_TITLE, task.getTitle());
    }

    @Test
    @SneakyThrows
    public void deleteById() {

        when(jdbcNamed.update(eq(QUERY_FOR_DELETE), anyMap())).thenReturn(1);

        int result = da.deleteById(TASK_ID);

        verify(jdbcNamed).update(eq(QUERY_FOR_DELETE), anyMap());

        assertEquals(1, result);

    }

    @Test
    @SneakyThrows
    public void saveUpdate() {

        TaskInfo task = TaskInfo.builder().id(TASK_ID).build();

        TaskInfo result = da.save(task);

        verify(jdbcNamed).update(eq(QUERY_FOR_UPDATE), anyMap());

        assertEquals(task.getId(), result.getId());

    }

    @Test
    @SneakyThrows
    public void saveInsert() {

        Number taskId = 1;
        TaskInfo task = TaskInfo.builder().build();
        when(jdbcInsert.executeAndReturnKey(anyMap())).thenReturn(taskId);

        TaskInfo resultTask = da.save(task);

        assertEquals(Long.valueOf(1), resultTask.getId());

        verify(jdbcInsert).executeAndReturnKey(anyMap());

    }
}