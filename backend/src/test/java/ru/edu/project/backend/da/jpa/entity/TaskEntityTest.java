package ru.edu.project.backend.da.jpa.entity;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class TaskEntityTest {

    public static final Long TASK_ID = 111L;
    public static final Long GROUP_ID = 222L;
    public static final Integer NUM = 123;
    public static final Timestamp START_DATE = new Timestamp(1L);
    public static final Timestamp END_DATE = new Timestamp(2L);
    public static final String TITLE = "Title";
    public static final String TEXT = "Text";


    @Test
    public void entityTest() {
        TaskEntity entity = new TaskEntity();
        entity.setId(TASK_ID);
        entity.setGroupId(GROUP_ID);
        entity.setNum(NUM);
        entity.setStartDate(START_DATE);
        entity.setEndDate(END_DATE);
        entity.setTitle(TITLE);
        entity.setText(TEXT);

        assertEquals(TASK_ID, entity.getId());
        assertEquals(GROUP_ID, entity.getGroupId());
        assertEquals(NUM, entity.getNum());
        assertEquals(START_DATE, entity.getStartDate());
        assertEquals(END_DATE, entity.getEndDate());
        assertEquals(TITLE, entity.getTitle());
        assertEquals(TEXT, entity.getText());
    }
}