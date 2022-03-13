package ru.edu.project.backend.da.jpa.entity;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class StudentEntityTest {

    public static final Long STUDENT_ID = 111L;
    public static final Long GROUP_ID = 222L;
    public static final String FIRST_NAME = "First";
    public static final String LAST_NAME = "Last";
    public static final String EMAIL = "Email";
    public static final Timestamp BIRTHDAY = new Timestamp(1L);
    public static final String PHONE = "Phone";


    @Test
    public void entityTest() {
        StudentEntity entity = new StudentEntity();
        entity.setId(STUDENT_ID);
        entity.setGroupId(GROUP_ID);
        entity.setFirstName(FIRST_NAME);
        entity.setLastName(LAST_NAME);
        entity.setEmail(EMAIL);
        entity.setBirthday(BIRTHDAY);
        entity.setPhone(PHONE);

        assertEquals(STUDENT_ID, entity.getId());
        assertEquals(GROUP_ID, entity.getGroupId());
        assertEquals(FIRST_NAME, entity.getFirstName());
        assertEquals(LAST_NAME, entity.getLastName());
        assertEquals(EMAIL, entity.getEmail());
        assertEquals(BIRTHDAY, entity.getBirthday());
        assertEquals(PHONE, entity.getPhone());
    }
}