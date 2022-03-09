package ru.edu.project.frontend.controller.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.authorization.UserDetailsId;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.students.StudentForm;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.frontend.controller.forms.students.StudentCreateForm;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class StudentTest {

    public static final Long STUDENT_ID = 111L;
    public static final Long GROUP_ID = 222L;
    public static final String STUDENT_ROLE = "student";
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String EMAIL = "email";
    public static final String PHONE = "8999999999";
    public static final Timestamp BIRTHDAY = new Timestamp(new Date().getTime());

    @Mock
    private UserDetailsId detailsId;


    @Mock
    private StudentService studentService;

    @Mock
    private SolutionService solutionService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model modelMock;


    @InjectMocks
    private Student student;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
        when(authentication.getPrincipal()).thenReturn(detailsId);
        when(detailsId.getUserId()).thenReturn(STUDENT_ID);
    }

    @Test
    public void index() {

        List<StudentInfo> studentList = new ArrayList<>();

        when(studentService.getAllStudents()).thenReturn(studentList);

        String viewName = student.index(modelMock, STUDENT_ROLE);

        assertEquals("person/index", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);
        verify(modelMock).addAttribute("students", studentList);
        verify(studentService, times(1)).getAllStudents();
    }


    @Test
    public void view() {

        StudentInfo expectedInfo = StudentInfo.builder().build();

        when(studentService.getById(STUDENT_ID)).thenReturn(expectedInfo);

        ModelAndView view = student.view(STUDENT_ID, STUDENT_ROLE);

        assertEquals("person/view", view.getViewName());
        assertEquals(expectedInfo, view.getModel().get("record"));
        assertEquals(STUDENT_ROLE, view.getModel().get("role"));

    }

    @Test
    public void delete() {

        when(solutionService.getSolutionsByStudent(STUDENT_ID)).thenReturn(null);

        String viewName = student.delete(modelMock, STUDENT_ID, STUDENT_ROLE);

        assertEquals("redirect:/" + STUDENT_ROLE + "/person/all", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);

        verify(studentService, times(1)).deleteById(STUDENT_ID);

    }

    @Test
    public void deleteHasErrors() {

        List<SolutionInfo> solutionsByTask = new ArrayList<>();

        solutionsByTask.add(SolutionInfo.builder().build());

        when(solutionService.getSolutionsByStudent(STUDENT_ID)).thenReturn(solutionsByTask);

        String viewName = student.delete(modelMock, STUDENT_ID, STUDENT_ROLE);

        assertEquals("person/deleteError", viewName);

        verify(modelMock, times(0)).addAttribute(anyString());

    }

    @Test
    public void createForm() {

        String viewName = student.createForm(modelMock, STUDENT_ROLE);

        assertEquals("person/create", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);
    }

    @Test
    public void createFormProcessing() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        StudentCreateForm studentCreateForm = mock(StudentCreateForm.class);
        when(studentCreateForm.getGroupId()).thenReturn(GROUP_ID);
        when(studentCreateForm.getFirstName()).thenReturn(FIRST_NAME);
        when(studentCreateForm.getLastName()).thenReturn(LAST_NAME);
        when(studentCreateForm.getBirthday()).thenReturn(BIRTHDAY);
        when(studentCreateForm.getEmail()).thenReturn(EMAIL);
        when(studentCreateForm.getPhone()).thenReturn(PHONE);

        StudentInfo expectedInfo = StudentInfo.builder().id(STUDENT_ID).build();

        when(studentService.createStudent(any(StudentForm.class))).thenAnswer(invocation -> {
            StudentForm form = invocation.getArgument(0, StudentForm.class);
            assertEquals(GROUP_ID, form.getGroupId());
            assertEquals(studentCreateForm.getBirthday(), form.getBirthday());
            assertEquals(studentCreateForm.getEmail(), form.getEmail());
            assertEquals(studentCreateForm.getFirstName(), form.getFirstName());
            assertEquals(studentCreateForm.getLastName(), form.getLastName());
            assertEquals(studentCreateForm.getPhone(), form.getPhone());

            return expectedInfo;
        });

        String viewName = student.createFormProcessing(studentCreateForm, bindingResultMock, modelMock, STUDENT_ROLE);

        assertEquals("redirect:/" + STUDENT_ROLE + "/person/all?created=" + STUDENT_ID, viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);
        verify(studentService).createStudent(any(StudentForm.class));
    }

    @Test
    public void createFormProcessingHasErrors() {

        List<ObjectError> mockErrors = new ArrayList<>();
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(true);
        when(bindingResultMock.getAllErrors()).thenReturn(mockErrors);

        StudentCreateForm createFormMock = mock(StudentCreateForm.class);

        String viewName = student.createFormProcessing(createFormMock, bindingResultMock, modelMock, STUDENT_ROLE);

        assertEquals("person/create", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);

        verify(modelMock).addAttribute("errorsList", mockErrors);
    }


    @Test
    public void editForm() {

        StudentInfo studentInfo = StudentInfo.builder().build();

        when(studentService.getById(STUDENT_ID)).thenReturn(studentInfo);

        ModelAndView view = student.editForm(STUDENT_ID, STUDENT_ROLE);

        assertEquals("person/edit", view.getViewName());

        assertEquals(studentInfo, view.getModel().get("record"));

        assertEquals(STUDENT_ROLE, view.getModel().get("role"));

    }

    @Test
    public void editFormProcessing() {

        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        StudentInfo expectedInfo = StudentInfo.builder().id(STUDENT_ID).build();

        StudentCreateForm studentCreateForm = mock(StudentCreateForm.class);

        when(studentCreateForm.getGroupId()).thenReturn(GROUP_ID);
        when(studentCreateForm.getFirstName()).thenReturn(FIRST_NAME);
        when(studentCreateForm.getLastName()).thenReturn(LAST_NAME);
        when(studentCreateForm.getBirthday()).thenReturn(BIRTHDAY);
        when(studentCreateForm.getEmail()).thenReturn(EMAIL);
        when(studentCreateForm.getPhone()).thenReturn(PHONE);


        when(studentService.editStudent(any(StudentForm.class))).thenAnswer(invocation -> {
            StudentForm form = invocation.getArgument(0, StudentForm.class);
            assertEquals(GROUP_ID, form.getGroupId());
            assertEquals(studentCreateForm.getBirthday(), form.getBirthday());
            assertEquals(studentCreateForm.getEmail(), form.getEmail());
            assertEquals(studentCreateForm.getFirstName(), form.getFirstName());
            assertEquals(studentCreateForm.getLastName(), form.getLastName());
            assertEquals(studentCreateForm.getPhone(), form.getPhone());
            return expectedInfo;
        });


        String viewName = student.editFormProcessing(studentCreateForm, STUDENT_ID, bindingResultMock, modelMock, STUDENT_ROLE);

        assertEquals("redirect:/" + STUDENT_ROLE + "/person/view/" + expectedInfo.getId(), viewName);

        verify(modelMock, times(1)).addAttribute("role", STUDENT_ROLE);

        verify(studentService).editStudent(any(StudentForm.class));
    }

    @Test
    public void editFormProcessingHasErrors() {

        List<ObjectError> mockErrors = new ArrayList<>();
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(true);
        when(bindingResultMock.getAllErrors()).thenReturn(mockErrors);

        StudentCreateForm createFormMock = mock(StudentCreateForm.class);

        String viewName = student.editFormProcessing(createFormMock, STUDENT_ID, bindingResultMock, modelMock, STUDENT_ROLE);

        assertEquals("person/create", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);

        verify(modelMock).addAttribute("errorsList", mockErrors);
    }

}