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
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.backend.api.tasks.TaskService;

import ru.edu.project.frontend.controller.forms.solutions.SolutionVerifyingForm;
import ru.edu.project.frontend.controller.forms.tasks.TaskCreateForm;
import ru.edu.project.frontend.controller.services.Group;
import ru.edu.project.frontend.controller.services.Solution;
import ru.edu.project.frontend.controller.services.Student;
import ru.edu.project.frontend.controller.services.Task;
import ru.edu.project.frontend.controller.users.TeacherController;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class TeacherControllerTest {

    public static final Long STUDENT_ID = 111L;

    public static final Long GROUP_ID = 222L;

    public static final Long SOLUTION_ID = 333L;

    public static final Long TASK_ID = 444L;

    public static final Integer TASK_NUM = 123;

    public static final String TEACHER_ROLE = "teacher";
    @Mock
    private UserDetailsId detailsId;

    @Mock
    private Solution solution;

    @Mock
    private Task task;

    @Mock
    private Student student;

    @Mock
    private Group group;

    @Mock
    private TaskService taskService;

    @Mock
    private StudentService studentService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model modelMock;


    @InjectMocks
    private TeacherController teacherController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
        when(authentication.getPrincipal()).thenReturn(detailsId);
        when(detailsId.getUserId()).thenReturn(STUDENT_ID);
    }

    @Test
    public void index() {

        String viewName = teacherController.index(modelMock);

        assertEquals("users/teacher/index", viewName);

    }

    @Test
    public void solutionIndex() {

        String searchBy = "";
        boolean isAsc = true;
        int page = 1;
        int perPage = 10;

        when(solution.index(searchBy, isAsc, page, perPage, "all", TEACHER_ROLE))
                .thenReturn(new ModelAndView());

        teacherController.solutionIndex(searchBy, isAsc, page, perPage);

        verify(solution).index(searchBy, isAsc, page, perPage, "all", TEACHER_ROLE);
    }

    @Test
    public void solutionView() {

        when(solution.view(SOLUTION_ID, TEACHER_ROLE))
                .thenReturn(new ModelAndView());

        teacherController.solutionView(SOLUTION_ID, authentication);

        verify(solution).view(SOLUTION_ID, TEACHER_ROLE);
    }

    @Test
    public void solutionReviewForm() {

        when(solution.reviewForm(modelMock, SOLUTION_ID, TEACHER_ROLE))
                .thenReturn("");

        teacherController.solutionReviewForm(modelMock, SOLUTION_ID);

        verify(solution).reviewForm(modelMock, SOLUTION_ID, TEACHER_ROLE);
    }

    @Test
    public void solutionVerifyForm() {

        when(solution.verifyForm(SOLUTION_ID, TASK_NUM, TEACHER_ROLE))
                .thenReturn(new ModelAndView());

        teacherController.solutionVerifyForm(SOLUTION_ID, TASK_NUM);

        verify(solution).verifyForm(SOLUTION_ID, TASK_NUM, TEACHER_ROLE);
    }

    @Test
    public void solutionVerifyFormProcessing() {

        SolutionVerifyingForm formMock = mock(SolutionVerifyingForm.class);
        BindingResult bindingResultMock = mock(BindingResult.class);

        when(solution.verifyFormProcessing(formMock, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, TEACHER_ROLE))
                .thenReturn("");

        teacherController.solutionVerifyFormProcessing(formMock, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock);

        verify(solution).verifyFormProcessing(formMock, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, TEACHER_ROLE);
    }

    @Test
    public void taskIndex() {

        when(task.index(modelMock, String.valueOf(GROUP_ID), TEACHER_ROLE))
                .thenReturn("");

        teacherController.taskIndex(modelMock, String.valueOf(GROUP_ID));

        verify(task).index(modelMock, String.valueOf(GROUP_ID), TEACHER_ROLE);
    }

    @Test
    public void taskView() {

        when(task.view(TASK_ID, TEACHER_ROLE))
                .thenReturn(new ModelAndView());

        teacherController.taskView(TASK_ID);

        verify(task).view(TASK_ID, TEACHER_ROLE);
    }

    @Test
    public void taskDelete() {

        when(task.delete(modelMock, TASK_ID, TEACHER_ROLE))
                .thenReturn("");

        teacherController.taskDelete(modelMock, TASK_ID);

        verify(task).delete(modelMock, TASK_ID, TEACHER_ROLE);
    }

    @Test
    public void taskCreateForm() {

        when(task.createForm(TEACHER_ROLE))
                .thenReturn(new ModelAndView());

        teacherController.taskCreateForm();

        verify(task).createForm(TEACHER_ROLE);
    }

    @Test
    public void taskCreateFormProcessing() {

        TaskCreateForm formMock = mock(TaskCreateForm.class);
        BindingResult bindingResultMock = mock(BindingResult.class);

        when(task.createFormProcessing(formMock, bindingResultMock, modelMock, TEACHER_ROLE))
                .thenReturn("");

        teacherController.taskCreateFormProcessing(formMock, bindingResultMock, modelMock);

        verify(task).createFormProcessing(formMock, bindingResultMock, modelMock, TEACHER_ROLE);
    }

    @Test
    public void taskEditForm() {

        when(task.editForm(TASK_ID, TEACHER_ROLE))
                .thenReturn(new ModelAndView());

        teacherController.taskEditForm(TASK_ID);

        verify(task).editForm(TASK_ID, TEACHER_ROLE);
    }

    @Test
    public void taskEditFormProcessing() {

        TaskCreateForm formMock = mock(TaskCreateForm.class);
        BindingResult bindingResultMock = mock(BindingResult.class);

        when(task.editFormProcessing(formMock, TASK_ID, bindingResultMock, modelMock, TEACHER_ROLE))
                .thenReturn("");

        teacherController.taskEditFormProcessing(formMock, TASK_ID, bindingResultMock, modelMock);

        verify(task).editFormProcessing(formMock, TASK_ID, bindingResultMock, modelMock, TEACHER_ROLE);
    }

    @Test
    public void groupIndex() {

        when(group.index(modelMock))
                .thenReturn("");

        teacherController.groupIndex(modelMock);

        verify(group).index(modelMock);
    }

    @Test
    public void personIndex() {

        when(student.index(modelMock, TEACHER_ROLE))
                .thenReturn("");

        teacherController.personIndex(modelMock);

        verify(student).index(modelMock, TEACHER_ROLE);
    }

    @Test
    public void personView() {

        when(student.view(STUDENT_ID, TEACHER_ROLE))
                .thenReturn(new ModelAndView());

        teacherController.personView(STUDENT_ID);

        verify(student).view(STUDENT_ID, TEACHER_ROLE);
    }

}