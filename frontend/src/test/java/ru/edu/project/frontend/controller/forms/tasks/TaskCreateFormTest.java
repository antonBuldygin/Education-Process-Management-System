package ru.edu.project.frontend.controller.forms.tasks;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class TaskCreateFormTest {

    public static final Long GROUP_ID = 111L;
    public static final Integer NUM = 123;
    public static final String TITLE = "Title";
    public static final String TEXT = "Text";
    public static final Timestamp START_TS = new Timestamp(1647018000000L);
    public static final Timestamp END_TS = new Timestamp(1647018000000L);

    @Test
    public void taskCreateFormTest() {

        TaskCreateForm taskCreateForm = new TaskCreateForm();

        taskCreateForm.setGroupId(GROUP_ID);
        taskCreateForm.setNum(NUM);
        taskCreateForm.setTitle(TITLE);
        taskCreateForm.setText(TEXT);
        taskCreateForm.setStartDate(START_TS.toString());
        taskCreateForm.setEndDate(END_TS.toString());

        assertEquals(GROUP_ID, taskCreateForm.getGroupId());
        assertEquals(NUM, taskCreateForm.getNum());
        assertEquals(TITLE, taskCreateForm.getTitle());
        assertEquals(TEXT, taskCreateForm.getText());
        assertNotNull(taskCreateForm.getStartDate());
        assertNotNull(taskCreateForm.getEndDate());

    }
}