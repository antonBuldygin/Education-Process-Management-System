package ru.edu.project.backend.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.edu.project.backend.api.students.StudentForm;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.tasks.TaskForm;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.service.StudentServiceLayer;
import ru.edu.project.backend.service.TaskServiceLayer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.openMocks;

public class StudentControllerTest {

    public static final long STUDENT_ID = 1L;
    @Mock
    StudentServiceLayer delegate;

    @InjectMocks
    StudentController studentController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void createTask() {
        StudentForm studentForm = mock(StudentForm.class);

        StudentInfo studentInfo = mock(StudentInfo.class);

        when(delegate.createStudent(studentForm)).thenReturn(studentInfo);

        StudentInfo result = studentController.createStudent(studentForm);

        assertEquals(studentInfo, result);

        verify(delegate, times(1)).createStudent(studentForm);
    }

    @Test
    public void editStudent() {
        StudentForm studentForm = mock(StudentForm.class);

        StudentInfo studentInfo = mock(StudentInfo.class);

        when(delegate.editStudent(studentForm)).thenReturn(studentInfo);

        StudentInfo result = studentController.editStudent(studentForm);

        assertEquals(studentInfo, result);

        verify(delegate, times(1)).editStudent(studentForm);
    }

    @Test
    public void getById() {

        StudentInfo studentInfo = mock(StudentInfo.class);

        when(delegate.getById(STUDENT_ID)).thenReturn(studentInfo);

        StudentInfo result = studentController.getById(STUDENT_ID);

        assertEquals(studentInfo, result);

        verify(delegate, times(1)).getById(STUDENT_ID);
    }

    @Test
    public void deleteById() {

        StudentInfo studentInfo = mock(StudentInfo.class);

        when(delegate.deleteById(STUDENT_ID)).thenReturn(1);

        int result = studentController.deleteById(STUDENT_ID);

        assertEquals(1, result);

        verify(delegate, times(1)).deleteById(STUDENT_ID);
    }

    @Test
    public void getAllStudents() {

        List<StudentInfo> result = studentController.getAllStudents();

        verify(delegate, times(1)).getAllStudents();
    }
}