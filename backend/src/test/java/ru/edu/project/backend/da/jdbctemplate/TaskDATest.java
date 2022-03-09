package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.edu.project.backend.api.tasks.TaskInfo;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static ru.edu.project.backend.da.jdbctemplate.Common.getInteger;
import static ru.edu.project.backend.da.jdbctemplate.TaskDA.QUERY_TASKS_BY_GROUP_ID;
import static ru.edu.project.backend.da.jdbctemplate.TaskDA.QUERY_TASK_BY_ID;


public class TaskDATest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TaskDA da;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    @SneakyThrows
    public void getTasksByGroup() {

        List<TaskInfo> expectedResult = new ArrayList();

        ResultSet resultSetMock = mock(ResultSet.class);
        Long taskId = 123L;
        Long groupId = 1234L;
        Integer num = 1;
        Timestamp startDate_ts = new Timestamp(1L);
        Timestamp endDate_ts = new Timestamp(2L);
        String title = "First task";
        String text = "Text of the first task";

        when(resultSetMock.getLong("id")).thenReturn(taskId);
        when(resultSetMock.getLong("group_id")).thenReturn(groupId);
        when(resultSetMock.getInt("num")).thenReturn(num);
        when(resultSetMock.getTimestamp("start_date")).thenReturn(startDate_ts);
        when(resultSetMock.getTimestamp("end_date")).thenReturn(endDate_ts);
        when(resultSetMock.getString("title")).thenReturn(title);
        when(resultSetMock.getString("text")).thenReturn("Text of the first task");

        when(jdbcTemplate.query(eq(QUERY_TASKS_BY_GROUP_ID), any(RowMapper.class), eq(groupId))).thenAnswer(invocation -> {
            RowMapper<TaskInfo> rowMapper = invocation.getArgument(1, RowMapper.class);
            expectedResult.add(rowMapper.mapRow(resultSetMock, 1));
            return expectedResult;
        });

        List<TaskInfo> list = da.getTasksByGroup(groupId);

        verify(jdbcTemplate).query(eq(QUERY_TASKS_BY_GROUP_ID), any(RowMapper.class), eq(groupId));

        assertEquals(1, list.size());

        TaskInfo info = list.get(0);
        assertEquals(taskId, info.getId());
        assertEquals(groupId, info.getGroupId());
        assertEquals(num, info.getNum());
        assertEquals(startDate_ts, info.getStartDate());
        assertEquals(endDate_ts, info.getEndDate());
        assertEquals(text, info.getText());
        assertEquals(title, info.getTitle());
    }
}