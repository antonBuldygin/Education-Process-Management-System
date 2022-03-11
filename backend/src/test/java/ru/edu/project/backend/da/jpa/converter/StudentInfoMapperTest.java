package ru.edu.project.backend.da.jpa.converter;

import org.junit.Before;
import org.junit.Test;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.da.jpa.entity.StudentEntity;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class StudentInfoMapperTest {


    public static final Long STUDENT_ID = 111L;
    public static final String FIRST_NAME = "First name";
    public static final String LAST_NAME = "Last name";
    public static final Long GROUP_ID = 222L;
    public static final String EMAIL = "Email";
    public static final Timestamp BIRTHDAY_TS = new Timestamp(1L);
    public static final String PHONE = "89995556677";

    StudentInfoMapper studentInfoMapper;

    @Before
    public void setUp() throws Exception {
        studentInfoMapper = new StudentInfoMapperImpl();
    }

    @Test
    public void entityToInfo() {
        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setId(STUDENT_ID);
        studentEntity.setFirstName(FIRST_NAME);
        studentEntity.setLastName(LAST_NAME);
        studentEntity.setGroupId(GROUP_ID);
        studentEntity.setEmail(EMAIL);
        studentEntity.setBirthday(BIRTHDAY_TS);
        studentEntity.setPhone(PHONE);

        StudentInfo studentInfo = studentInfoMapper.map(studentEntity);

        assertEquals(STUDENT_ID, studentInfo.getId());
        assertEquals(FIRST_NAME, studentInfo.getFirstName());
        assertEquals(LAST_NAME, studentInfo.getLastName());
        assertEquals(GROUP_ID, studentInfo.getGroupId());
        assertEquals(EMAIL, studentInfo.getEmail());
        assertEquals(BIRTHDAY_TS, studentInfo.getBirthday());
        assertEquals(PHONE, studentInfo.getPhone());

    }
}