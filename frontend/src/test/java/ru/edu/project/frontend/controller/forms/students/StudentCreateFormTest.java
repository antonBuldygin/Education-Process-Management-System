package ru.edu.project.frontend.controller.forms.students;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class StudentCreateFormTest {

    public static final Long GROUP_ID = 111L;
    public static final String FIRSTNAME = "Firstname";
    public static final String LASTNAME = "Lastname";
    public static final String EMAIL = "Email";
    public static final String PHONE = "8999944445656";
    public static final Timestamp BIRTHDAY_TS = new Timestamp(1647018000000L);

    @Test
    public void studentCreateFormTest() {
        StudentCreateForm studentCreateForm = new StudentCreateForm();

        studentCreateForm.setGroupId(GROUP_ID);
        studentCreateForm.setFirstName(FIRSTNAME);
        studentCreateForm.setLastName(LASTNAME);
        studentCreateForm.setEmail(EMAIL);
        studentCreateForm.setPhone(PHONE);
        studentCreateForm.setBirthday(BIRTHDAY_TS.toString());

        assertEquals(GROUP_ID, studentCreateForm.getGroupId());
        assertEquals(FIRSTNAME, studentCreateForm.getFirstName());
        assertEquals(LASTNAME, studentCreateForm.getLastName());
        assertEquals(EMAIL, studentCreateForm.getEmail());
        assertEquals(PHONE, studentCreateForm.getPhone());
        assertEquals(BIRTHDAY_TS, studentCreateForm.getBirthday());

    }

}