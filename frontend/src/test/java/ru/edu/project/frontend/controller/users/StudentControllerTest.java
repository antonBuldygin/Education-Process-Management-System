package ru.edu.project.frontend.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.authorization.UserDetailsId;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.backend.api.tasks.TaskService;
import ru.edu.project.frontend.controller.forms.solutions.SolutionCreateForm;
import ru.edu.project.frontend.controller.forms.solutions.SolutionUploadForm;
import ru.edu.project.frontend.controller.services.Solution;
import ru.edu.project.frontend.controller.services.Student;
import ru.edu.project.frontend.controller.services.Task;
import ru.edu.project.frontend.controller.users.StudentController;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class StudentControllerTest {

    public static final Long STUDENT_ID = 111L;

    public static final Long GROUP_ID = 222L;

    public static final Long SOLUTION_ID = 333L;

    public static final Long TASK_ID = 444L;

    public static final Integer TASK_NUM = 123;

    public static final String STUDENT_ROLE = "student";
    @Mock
    private UserDetailsId detailsId;

    @Mock
    private Solution solution;

    @Mock
    private Task task;

    @Mock
    private Student student;

    @Mock
    private TaskService taskService;

    @Mock
    private StudentService studentService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model modelMock;


    @InjectMocks
    private StudentController studentController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
        when(authentication.getPrincipal()).thenReturn(detailsId);
        when(detailsId.getUserId()).thenReturn(STUDENT_ID);
    }

    @Test
    public void index() {
        StudentInfo studentInfo = StudentInfo.builder().groupId(GROUP_ID).build();
        when(studentService.getById(STUDENT_ID)).thenReturn(studentInfo);

        String viewName = studentController.index(modelMock, authentication);

        assertEquals("users/student/index", viewName);

        verify(modelMock).addAttribute("studentId", STUDENT_ID);
        verify(modelMock).addAttribute("groupId", GROUP_ID);
    }

    @Test
    public void solutionIndex() {

        String searchBy = "";
        boolean isAsc = true;
        int page = 1;
        int perPage = 10;

        when(solution.index(searchBy, isAsc, page, perPage, String.valueOf(STUDENT_ID), STUDENT_ROLE))
                .thenReturn(new ModelAndView());

        studentController.solutionIndex(searchBy, isAsc, page, perPage, authentication);

        verify(solution).index(searchBy, isAsc, page, perPage, String.valueOf(STUDENT_ID), STUDENT_ROLE);
    }

    @Test
    public void solutionView() {

        when(solution.view(SOLUTION_ID, STUDENT_ROLE))
                .thenReturn(new ModelAndView());

        studentController.solutionView(SOLUTION_ID, authentication);

        verify(solution).view(SOLUTION_ID, STUDENT_ROLE);
    }

    @Test
    public void solutionCreateForm() {

        when(solution.createForm(modelMock, STUDENT_ID, STUDENT_ROLE))
                .thenReturn("solution/create");

        studentController.solutionCreateForm(modelMock, authentication);

        verify(solution).createForm(modelMock, STUDENT_ID, STUDENT_ROLE);
    }

    @Test
    public void solutionCreateFormProcessing() {

        SolutionCreateForm formMock = mock(SolutionCreateForm.class);
        BindingResult bindingResultMock = mock(BindingResult.class);

        when(solution.createFormProcessing(formMock, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE))
                .thenReturn("");

        studentController.solutionCreateFormProcessing(formMock, bindingResultMock, modelMock, authentication);

        verify(solution).createFormProcessing(formMock, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE);
    }

    @Test
    public void solutionReuploadForm() {

        when(solution.reuploadForm(SOLUTION_ID, TASK_NUM, STUDENT_ROLE))
                .thenReturn(new ModelAndView());

        studentController.solutionReuploadForm(SOLUTION_ID, TASK_NUM, authentication);

        verify(solution).reuploadForm(SOLUTION_ID, TASK_NUM, STUDENT_ROLE);
    }

    @Test
    public void solutionReuploadFormProcessing() {

        SolutionUploadForm formMock = mock(SolutionUploadForm.class);
        BindingResult bindingResultMock = mock(BindingResult.class);

        when(solution.reuploadFormProcessing(formMock, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE))
                .thenReturn("");

        studentController.solutionReuploadFormProcessing(formMock, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, authentication);

        verify(solution).reuploadFormProcessing(formMock, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE);
    }

    @Test
    public void solutionUploadForm() {

        when(solution.uploadForm(SOLUTION_ID, TASK_NUM, STUDENT_ROLE))
                .thenReturn(new ModelAndView());

        studentController.solutionUploadForm(SOLUTION_ID, TASK_NUM, authentication);

        verify(solution).uploadForm(SOLUTION_ID, TASK_NUM, STUDENT_ROLE);
    }

    @Test
    public void solutionUploadFormProcessing() {

        SolutionUploadForm formMock = mock(SolutionUploadForm.class);
        BindingResult bindingResultMock = mock(BindingResult.class);

        when(solution.uploadFormProcessing(formMock, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE))
                .thenReturn("");

        studentController.solutionUploadFormProcessing(formMock, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, authentication);

        verify(solution).uploadFormProcessing(formMock, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE);
    }

    @Test
    public void taskIndex() {

        when(task.index(modelMock, String.valueOf(GROUP_ID), STUDENT_ROLE))
                .thenReturn("");

        studentController.taskIndex(modelMock, String.valueOf(GROUP_ID), authentication);

        verify(task).index(modelMock, String.valueOf(GROUP_ID), STUDENT_ROLE);
    }

    @Test
    public void taskView() {

        when(task.view(TASK_ID, STUDENT_ROLE))
                .thenReturn(new ModelAndView());

        studentController.taskView(TASK_ID, authentication);

        verify(task).view(TASK_ID, STUDENT_ROLE);
    }

    @Test
    public void personView() {

        when(student.view(STUDENT_ID, STUDENT_ROLE))
                .thenReturn(new ModelAndView());

        studentController.personView(STUDENT_ID);

        verify(student).view(STUDENT_ID, STUDENT_ROLE);
    }

}