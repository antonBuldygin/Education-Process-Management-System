package ru.edu.project.backend.da.jpa;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.da.jpa.converter.TaskInfoMapper;
import ru.edu.project.backend.da.jpa.entity.TaskEntity;
import ru.edu.project.backend.da.jpa.repository.TaskEntityRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class TaskJpaDaTest {

    public static final Long GROUP_ID = 111L;
    public static final Long TASK_ID = 222L;
    @Mock
    TaskEntityRepository repo;

    @Mock
    TaskInfoMapper mapper;

    @InjectMocks
    TaskJpaDa taskJpaDa;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void getAvailable() {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        List<TaskEntity> taskEntities = new ArrayList<>();

        Timestamp date = mock(Timestamp.class);

        when(repo.getAvailable(date, GROUP_ID)).thenReturn(taskEntities);

        when(mapper.map(taskEntities)).thenReturn(taskInfoList);

        List<TaskInfo> tasks = taskJpaDa.getAvailable(date, GROUP_ID);

        verify(repo,times(1)).getAvailable(date, GROUP_ID);
        verify(mapper,times(1)).map(taskEntities);
    }

    @Test
    public void save() {
        TaskEntity taskEntity = mock(TaskEntity.class);
        TaskInfo taskInfo = mock(TaskInfo.class);

        when(mapper.map(taskInfo)).thenReturn(taskEntity);

        when(repo.save(any(TaskEntity.class))).thenAnswer(invocation -> {
            TaskEntity task = new TaskEntity();
            task.setId(TASK_ID);
            return task;
        });

        when(mapper.map(any(TaskEntity.class))).thenAnswer(invocation -> {
            TaskEntity task = invocation.getArgument(0, TaskEntity.class);
            TaskInfo taskInf = TaskInfo.builder().id(task.getId()).build();
            return taskInf;
        });

        TaskInfo result = taskJpaDa.save(taskInfo);

        assertEquals(TASK_ID, result.getId());

        verify(mapper, times(1)).map(taskInfo);

        verify(repo).save(any(TaskEntity.class));

        verify(mapper).map(any(TaskEntity.class));
    }

    @Test
    public void getTasksByGroup() {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        List<TaskEntity> taskEntities = new ArrayList<>();

        when(repo.findByGroupId(GROUP_ID)).thenReturn(taskEntities);

        when(mapper.map(taskEntities)).thenReturn(taskInfoList);

        List<TaskInfo> tasks = taskJpaDa.getTasksByGroup(GROUP_ID);

        verify(repo,times(1)).findByGroupId(GROUP_ID);
        verify(mapper,times(1)).map(taskEntities);
    }

    @Test
    public void getAllTasks() {
        List<TaskInfo> taskInfoList = new ArrayList<>();

        List<TaskEntity> taskEntities = new ArrayList<>();

        when(repo.findAll()).thenReturn(taskEntities);

        when(mapper.map(taskEntities)).thenReturn(taskInfoList);

        List<TaskInfo> tasks = taskJpaDa.getAllTasks();

        verify(repo,times(1)).findAll();
        verify(mapper,times(1)).map(taskEntities);
    }


    @Test
    public void getById() {

        TaskInfo taskInfo = TaskInfo.builder().id(TASK_ID).build();

        when(mapper.map(any(TaskEntity.class))).thenReturn(taskInfo);

        TaskInfo result = taskJpaDa.getById(TASK_ID);

        verify(repo,times(1)).findById(TASK_ID);

    }

    @Test
    public void deleteById() {
        Integer result = taskJpaDa.deleteById(TASK_ID);

        verify(repo).deleteById(TASK_ID);
    }
}