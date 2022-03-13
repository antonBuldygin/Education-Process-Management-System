package ru.edu.project.backend.da.jpa;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.da.jpa.converter.StudentInfoMapper;
import ru.edu.project.backend.da.jpa.entity.StudentEntity;
import ru.edu.project.backend.da.jpa.entity.StudentEntity;
import ru.edu.project.backend.da.jpa.entity.StudentEntity;
import ru.edu.project.backend.da.jpa.repository.StudentEntityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class StudentJpaDaTest {

    public static final Long STUDENT_ID = 111L;
    @Mock
    StudentEntityRepository repo;

    @Mock
    StudentInfoMapper mapper;

    @InjectMocks
    StudentJpaDa studentJpaDa;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void save() {
        StudentEntity studentEntity = mock(StudentEntity.class);
        StudentInfo studentInfo = mock(StudentInfo.class);

        when(mapper.map(studentInfo)).thenReturn(studentEntity);

        when(repo.save(any(StudentEntity.class))).thenAnswer(invocation -> {
            StudentEntity student = new StudentEntity();
            student.setId(STUDENT_ID);
            return student;
        });

        when(mapper.map(any(StudentEntity.class))).thenAnswer(invocation -> {
            StudentEntity student = invocation.getArgument(0, StudentEntity.class);
            StudentInfo studentInf = StudentInfo.builder().id(student.getId()).build();
            return studentInf;
        });

        StudentInfo result = studentJpaDa.save(studentInfo);

        assertEquals(STUDENT_ID, result.getId());

        verify(mapper, times(1)).map(studentInfo);

        verify(repo).save(any(StudentEntity.class));

        verify(mapper).map(any(StudentEntity.class));
    }

    @Test
    public void getAllStudents() {
        List<StudentInfo> studentInfoList = new ArrayList<>();

        List<StudentEntity> studentEntities = new ArrayList<>();

        when(repo.findAll()).thenReturn(studentEntities);

        when(mapper.map(studentEntities)).thenReturn(studentInfoList);

        List<StudentInfo> students = studentJpaDa.getAllStudents();

        verify(repo,times(1)).findAll();
        verify(mapper,times(1)).map(studentEntities);
    }

    @Test
    public void getById() {

        StudentInfo studentInfo = StudentInfo.builder().id(STUDENT_ID).build();

        when(mapper.map(any(StudentEntity.class))).thenReturn(studentInfo);

        StudentInfo result = studentJpaDa.getById(STUDENT_ID);

        verify(repo,times(1)).findById(STUDENT_ID);

    }

    @Test
    public void deleteById() {
        Integer result = studentJpaDa.deleteById(STUDENT_ID);

        verify(repo).deleteById(STUDENT_ID);
    }

}