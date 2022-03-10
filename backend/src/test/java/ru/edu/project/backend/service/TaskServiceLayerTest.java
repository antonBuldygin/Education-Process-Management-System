package ru.edu.project.backend.service;

import javafx.concurrent.Task;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.edu.project.backend.api.tasks.TaskForm;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.da.SolutionDALayer;
import ru.edu.project.backend.da.TaskDALayer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class TaskServiceLayerTest {

    public static final Long GROUP_ID = 111L;
    public static final Long TASK_ID = 222L;
    public static final Integer TASK_NUM = 123;
    public static final String TASK_TITLE = "Task title";
    public static final String TASK_TEXT = "Task text";
    public static final Timestamp TASK_START_TS = new Timestamp(1L);
    public static final Timestamp TASK_END_TS = new Timestamp(2L);
    @Mock
    TaskDALayer daLayer;

    @InjectMocks
    TaskServiceLayer taskServiceLayer;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void getAvailable() {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        when(daLayer.getAvailable(any(Timestamp.class), eq(GROUP_ID))).thenReturn(taskInfoList);

        List<TaskInfo> result = taskServiceLayer.getAvailable(GROUP_ID);

        assertNotNull(result);

        verify(daLayer).getAvailable(any(Timestamp.class), eq(GROUP_ID));
    }


    @Test
    public void createTask() {
        TaskForm taskForm = taskBuilder();

        TaskInfo taskInfo = taskServiceLayer.createTask(taskForm);

        assertNull(taskInfo.getId());
        assertEquals(taskForm.getGroupId(), taskInfo.getGroupId());
        assertEquals(taskForm.getNum(), taskInfo.getNum());
        assertEquals(taskForm.getTitle(), taskInfo.getTitle());
        assertEquals(taskForm.getText(), taskInfo.getText());
        assertEquals(taskForm.getStartDate(), taskInfo.getStartDate());
        assertEquals(taskForm.getEndDate(), taskInfo.getEndDate());

        verify(daLayer, times(1)).save(any());
    }

    @Test
    public void editTask() {
        TaskForm taskForm = taskBuilder();

        TaskInfo taskInfo = taskServiceLayer.editTask(taskForm);

        assertEquals(taskForm.getId(), taskInfo.getId());
        assertEquals(taskForm.getGroupId(), taskInfo.getGroupId());
        assertEquals(taskForm.getNum(), taskInfo.getNum());
        assertEquals(taskForm.getTitle(), taskInfo.getTitle());
        assertEquals(taskForm.getText(), taskInfo.getText());
        assertEquals(taskForm.getStartDate(), taskInfo.getStartDate());
        assertEquals(taskForm.getEndDate(), taskInfo.getEndDate());

        verify(daLayer, times(1)).save(any());
    }

    @Test
    public void getTasksByGroup() {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        when(daLayer.getTasksByGroup(GROUP_ID)).thenReturn(taskInfoList);

        List<TaskInfo> result = taskServiceLayer.getTasksByGroup(GROUP_ID);

        assertNotNull(taskInfoList);

        verify(daLayer).getTasksByGroup(GROUP_ID);
    }

    @Test
    public void getAllTasks() {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        when(daLayer.getAllTasks()).thenReturn(taskInfoList);

        List<TaskInfo> result = taskServiceLayer.getAllTasks();

        assertNotNull(taskInfoList);

        verify(daLayer).getAllTasks();
    }

    @Test
    public void getById() {

        TaskInfo taskInfo = TaskInfo.builder().id(TASK_ID).build();

        when(daLayer.getById(TASK_ID)).thenReturn(taskInfo);

        TaskInfo result = taskServiceLayer.getById(TASK_ID);

        assertEquals(TASK_ID, result.getId());

        verify(daLayer).getById(TASK_ID);

    }

    @Test
    public void deleteById() {

        when(daLayer.deleteById(TASK_ID)).thenReturn(1);

        Integer result = taskServiceLayer.deleteById(TASK_ID);

        assertEquals((Integer) 1, result);

        verify(daLayer).deleteById(TASK_ID);

    }

    private TaskForm taskBuilder() {
        return TaskForm.builder()
                .id(TASK_ID)
                .groupId(GROUP_ID)
                .num(TASK_NUM)
                .title(TASK_TITLE)
                .text(TASK_TEXT)
                .startDate(TASK_START_TS)
                .endDate(TASK_END_TS)
                .build();
    }
}