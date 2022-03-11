package ru.edu.project.backend.da.jpa.converter;

import org.junit.Before;
import org.junit.Test;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.da.jpa.entity.TaskEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TaskInfoMapperTest {


    public static final Long TASK_ID = 111L;
    public static final Long GROUP_ID = 222L;
    public static final Integer TASK_NUM = 123;
    public static final String TITLE = "Title";
    public static final String TEXT = "Text";
    public static final Timestamp START_DATE_TS = new Timestamp(1L);
    public static final Timestamp END_DATE_TS = new Timestamp(1L);
    public static final String TASK_TEXT = "Task text";
    public static final String TASK_TITLE = "Task title";


    TaskInfoMapper taskInfoMapper;

    @Before
    public void setUp() throws Exception {
        taskInfoMapper = new TaskInfoMapperImpl();
    }

    @Test
    public void entityToInfo() {

        TaskEntity entity = new TaskEntity();
        entity.setId(TASK_ID);
        entity.setGroupId(GROUP_ID);
        entity.setNum(TASK_NUM);
        entity.setTitle(TITLE);
        entity.setText(TEXT);
        entity.setStartDate(START_DATE_TS);
        entity.setEndDate(END_DATE_TS);

        TaskInfo result = taskInfoMapper.map(entity);

        assertEquals(TASK_ID, result.getId());
        assertEquals(GROUP_ID, result.getGroupId());
        assertEquals(TASK_NUM, result.getNum());
        assertEquals(TITLE, result.getTitle());
        assertEquals(TEXT, result.getText());
        assertEquals(START_DATE_TS, result.getStartDate());
        assertEquals(END_DATE_TS, result.getEndDate());
    }

    @Test
    public void entityToInfoNull() {
        TaskEntity entity = null;

        TaskInfo result = taskInfoMapper.map(entity);

        assertNull(result);
    }

    @Test
    public void infoToEntity() {

        TaskInfo taskInfo = TaskInfo.builder()
                .id(TASK_ID)
                .groupId(GROUP_ID)
                .num(TASK_NUM)
                .title(TASK_TITLE)
                .text(TASK_TEXT)
                .startDate(START_DATE_TS)
                .endDate(END_DATE_TS)
                .build();

        TaskEntity taskEntity = taskInfoMapper.map(taskInfo);

        assertEquals(TASK_ID, taskEntity.getId());
        assertEquals(GROUP_ID, taskEntity.getGroupId());
        assertEquals(TASK_NUM, taskEntity.getNum());
        assertEquals(TASK_TITLE, taskEntity.getTitle());
        assertEquals(TASK_TEXT, taskEntity.getText());
        assertEquals(START_DATE_TS, taskEntity.getStartDate());
        assertEquals(END_DATE_TS, taskEntity.getEndDate());
    }

    @Test
    public void infoToEntityNull() {

        TaskInfo taskInfo = null;

        TaskEntity taskEntity = taskInfoMapper.map(taskInfo);

        assertNull(taskEntity);
    }

    @Test
    public void iterableEntityToInfo() {
        TaskEntity entity = new TaskEntity();

        entity.setId(TASK_ID);
        entity.setGroupId(GROUP_ID);
        entity.setNum(TASK_NUM);
        entity.setTitle(TITLE);
        entity.setText(TEXT);
        entity.setStartDate(START_DATE_TS);
        entity.setEndDate(END_DATE_TS);

        List<TaskEntity> taskEntityList = new ArrayList<>();

        taskEntityList.add(entity);

        List<TaskInfo> resultList = taskInfoMapper.map(taskEntityList);

        assertEquals(1, resultList.size());

        TaskInfo result = resultList.get(0);

        assertEquals(TASK_ID, result.getId());
        assertEquals(GROUP_ID, result.getGroupId());
        assertEquals(TASK_NUM, result.getNum());
        assertEquals(TITLE, result.getTitle());
        assertEquals(TEXT, result.getText());
        assertEquals(START_DATE_TS, result.getStartDate());
        assertEquals(END_DATE_TS, result.getEndDate());

    }

    @Test
    public void iterableEntityToInfoNull() {
        List<TaskEntity> entityList = null;

        List<TaskInfo> result = taskInfoMapper.map(entityList);

        assertNull(result);
    }

}