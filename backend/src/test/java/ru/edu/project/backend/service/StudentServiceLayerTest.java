package ru.edu.project.backend.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.edu.project.backend.api.students.StudentForm;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.da.StudentDALayer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class StudentServiceLayerTest {

    public static final Long GROUP_ID = 111L;
    public static final Long TASK_ID = 222L;
    public static final String STUDENT_FIRST_NAME = "Ivan";
    public static final String STUDENT_LAST_NAME = "Ivanov";
    public static final Timestamp STUDENT_BIRTHDAY_TS = new Timestamp(4L);
    public static final String STUDENT_PHONE = "89998887766";
    public static final String STUDENT_EMAIL = "ivanov@mail.ru";
    public static final Long STUDENT_ID = 444L;

    @Mock
    StudentDALayer daLayer;

    @InjectMocks
    StudentServiceLayer studentServiceLayer;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void createStudent() {
        StudentForm studentForm = studentBuilder();

        StudentInfo studentInfo = studentServiceLayer.createStudent(studentForm);

        assertNull(studentInfo.getId());
        assertEquals(studentForm.getGroupId(), studentInfo.getGroupId());
        assertEquals(studentForm.getFirstName(), studentInfo.getFirstName());
        assertEquals(studentForm.getLastName(), studentInfo.getLastName());
        assertEquals(studentForm.getBirthday(), studentInfo.getBirthday());
        assertEquals(studentForm.getEmail(), studentInfo.getEmail());
        assertEquals(studentForm.getPhone(), studentInfo.getPhone());

        verify(daLayer, times(1)).save(any());
    }

    @Test
    public void editStudent() {
        StudentForm studentForm = studentBuilder();

        StudentInfo studentInfo = studentServiceLayer.editStudent(studentForm);

//        assertEquals(studentForm.getId(), studentInfo.getId());
        assertEquals(studentForm.getGroupId(), studentInfo.getGroupId());
        assertEquals(studentForm.getFirstName(), studentInfo.getFirstName());
        assertEquals(studentForm.getLastName(), studentInfo.getLastName());
        assertEquals(studentForm.getBirthday(), studentInfo.getBirthday());
        assertEquals(studentForm.getEmail(), studentInfo.getEmail());
        assertEquals(studentForm.getPhone(), studentInfo.getPhone());

        verify(daLayer, times(1)).save(any());
    }

    @Test
    public void getAllStudents() {
        List<StudentInfo> studentInfoList = new ArrayList<>();

        when(daLayer.getAllStudents()).thenReturn(studentInfoList);

        List<StudentInfo> result = studentServiceLayer.getAllStudents();

        assertNotNull(studentInfoList);

        verify(daLayer).getAllStudents();
    }

    @Test
    public void getById() {

        StudentInfo studentInfo = StudentInfo.builder().id(STUDENT_ID).build();

        when(daLayer.getById(STUDENT_ID)).thenReturn(studentInfo);

        StudentInfo result = studentServiceLayer.getById(STUDENT_ID);

        assertEquals(STUDENT_ID, result.getId());

        verify(daLayer).getById(STUDENT_ID);

    }

    @Test
    public void deleteById() {

        when(daLayer.deleteById(STUDENT_ID)).thenReturn(1);

        Integer result = studentServiceLayer.deleteById(STUDENT_ID);

        assertEquals((Integer) 1, result);

        verify(daLayer).deleteById(STUDENT_ID);

    }

    private StudentForm studentBuilder() {
        return StudentForm.builder()
//                .id(STUDENT_ID)
                .firstName(STUDENT_FIRST_NAME)
                .lastName(STUDENT_LAST_NAME)
                .birthday(STUDENT_BIRTHDAY_TS)
                .phone(STUDENT_PHONE)
                .email(STUDENT_EMAIL)
                .build();
    }

}