package ru.edu.project.backend.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.edu.project.backend.api.tasks.TaskForm;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.service.TaskServiceLayer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class TaskControllerTest {

    public static final long GROUP_ID = 1L;

    public static final long TASK_ID = 2L;

    @Mock
    TaskServiceLayer delegate;

    @InjectMocks
    TaskController taskController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void getAvailable() {
        List<TaskInfo> taskInfos = new ArrayList<>();

        when(delegate.getAvailable(GROUP_ID)).thenReturn(taskInfos);

        List<TaskInfo> result = taskController.getAvailable(GROUP_ID);

        verify(delegate, times(1)).getAvailable(GROUP_ID);
    }

    @Test
    public void createTask() {
        TaskForm taskForm = mock(TaskForm.class);

        TaskInfo taskInfo = mock(TaskInfo.class);

        when(delegate.createTask(taskForm)).thenReturn(taskInfo);

        TaskInfo result = taskController.createTask(taskForm);

        assertEquals(taskInfo, result);

        verify(delegate, times(1)).createTask(taskForm);
    }

    @Test
    public void editTask() {
        TaskForm taskForm = mock(TaskForm.class);

        TaskInfo taskInfo = mock(TaskInfo.class);

        when(delegate.editTask(taskForm)).thenReturn(taskInfo);

        TaskInfo result = taskController.editTask(taskForm);

        assertEquals(taskInfo, result);

        verify(delegate, times(1)).editTask(taskForm);
    }

    @Test
    public void getTasksByGroup() {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        when(delegate.getTasksByGroup(GROUP_ID)).thenReturn(taskInfoList);

        List<TaskInfo> result = taskController.getTasksByGroup(GROUP_ID);

        verify(delegate, times(1)).getTasksByGroup(GROUP_ID);
    }

    @Test
    public void getAllTasks() {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        when(delegate.getAllTasks()).thenReturn(taskInfoList);

        List<TaskInfo> result = taskController.getAllTasks();

        verify(delegate, times(1)).getAllTasks();
    }

    @Test
    public void getById() {

        TaskInfo taskInfo = mock(TaskInfo.class);

        when(delegate.getById(TASK_ID)).thenReturn(taskInfo);

        TaskInfo result = taskController.getById(TASK_ID);

        assertEquals(taskInfo, result);

        verify(delegate, times(1)).getById(TASK_ID);
    }

    @Test
    public void deleteById() {

        when(delegate.deleteById(TASK_ID)).thenReturn(1);

        Integer result = taskController.deleteById(TASK_ID);

        assertEquals((Integer)1, result);

        verify(delegate, times(1)).deleteById(TASK_ID);
    }
}