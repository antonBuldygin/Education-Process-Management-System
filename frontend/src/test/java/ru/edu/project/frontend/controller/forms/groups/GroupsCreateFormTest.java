package ru.edu.project.frontend.controller.forms.groups;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class GroupsCreateFormTest {

    public static final List<Long> TEACHERS = new ArrayList<>();
    public static final String COMMENT = "Comment";
    public static final Timestamp CREATED_TS = new Timestamp(1644427800000L);
    public static final String CREATED_TS_STRING = "2022-02-10T12:30";


    @Test
    public void groupCreateFormTest() {

        GroupsCreateForm groupCreateForm = new GroupsCreateForm();

        groupCreateForm.setTeachers(TEACHERS);
        groupCreateForm.setComment(COMMENT);
        groupCreateForm.setDateCreatedForm(CREATED_TS_STRING);

        assertEquals(TEACHERS, groupCreateForm.getTeachers());
        assertEquals(COMMENT, groupCreateForm.getComment());
        assertEquals(CREATED_TS, groupCreateForm.getDateCreated());
    }
}