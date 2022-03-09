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
import ru.edu.project.frontend.controller.forms.groups.GroupsCreateForm;
import ru.edu.project.frontend.controller.forms.solutions.SolutionCreateForm;
import ru.edu.project.frontend.controller.forms.solutions.SolutionUploadForm;
import ru.edu.project.frontend.controller.forms.students.StudentCreateForm;
import ru.edu.project.frontend.controller.services.Group;
import ru.edu.project.frontend.controller.services.Solution;
import ru.edu.project.frontend.controller.services.Student;
import ru.edu.project.frontend.controller.services.Task;
import ru.edu.project.frontend.controller.users.AdminController;
import ru.edu.project.frontend.controller.users.StudentController;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class AdminControllerTest {

    public static final Long STUDENT_ID = 111L;

    public static final Long GROUP_ID = 222L;

    public static final Long SOLUTION_ID = 333L;

    public static final Long TASK_ID = 444L;

    public static final Integer TASK_NUM = 123;

    public static final String ADMIN_ROLE = "admin";
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
    private AdminController adminController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
        when(authentication.getPrincipal()).thenReturn(detailsId);
        when(detailsId.getUserId()).thenReturn(STUDENT_ID);
    }

    @Test
    public void index() {

        String viewName = adminController.index(modelMock);

        assertEquals("users/admin/index", viewName);

    }

    @Test
    public void solutionIndex() {

        String searchBy = "";
        boolean isAsc = true;
        int page = 1;
        int perPage = 10;

        when(solution.index(searchBy, isAsc, page, perPage, "all", ADMIN_ROLE))
                .thenReturn(new ModelAndView());

        adminController.solutionIndex(searchBy, isAsc, page, perPage);

        verify(solution).index(searchBy, isAsc, page, perPage, "all", ADMIN_ROLE);
    }


    @Test
    public void taskIndex() {

        when(task.index(modelMock, String.valueOf(GROUP_ID), ADMIN_ROLE))
                .thenReturn("");

        adminController.taskIndex(modelMock, String.valueOf(GROUP_ID), authentication);

        verify(task).index(modelMock, String.valueOf(GROUP_ID), ADMIN_ROLE);
    }

    @Test
    public void taskView() {

        when(task.view(TASK_ID, ADMIN_ROLE))
                .thenReturn(new ModelAndView());

        adminController.taskView(TASK_ID);

        verify(task).view(TASK_ID, ADMIN_ROLE);
    }

    @Test
    public void groupIndex() {

        when(group.index(modelMock))
                .thenReturn("");

        adminController.groupIndex(modelMock);

        verify(group).index(modelMock);
    }

    @Test
    public void groupView() {

        when(group.view(GROUP_ID))
                .thenReturn(new ModelAndView());

        adminController.groupView(GROUP_ID);

        verify(group).view(GROUP_ID);
    }

    @Test
    public void groupDeleteForm() {

        when(group.deleteForm(GROUP_ID))
                .thenReturn("");

        adminController.groupDeleteForm(GROUP_ID);

        verify(group).deleteForm(GROUP_ID);
    }

    @Test
    public void groupCreateForm() {

        when(group.createForm(modelMock))
                .thenReturn("");

        adminController.groupCreateForm(modelMock);

        verify(group).createForm(modelMock);
    }

    @Test
    public void groupCreateFormProcessing() {

        GroupsCreateForm formMock = mock(GroupsCreateForm.class);
        BindingResult bindingResultMock = mock(BindingResult.class);

        when(group.createFormProcessing(formMock, bindingResultMock, modelMock))
                .thenReturn("");

        adminController.groupCreateFormProcessing(formMock, bindingResultMock, modelMock);

        verify(group).createFormProcessing(formMock, bindingResultMock, modelMock);
    }

    @Test
    public void personIndex() {

        when(student.index(modelMock, ADMIN_ROLE))
                .thenReturn("");

        adminController.personIndex(modelMock);

        verify(student).index(modelMock, ADMIN_ROLE);
    }

    @Test
    public void personView() {

        when(student.view(STUDENT_ID, ADMIN_ROLE))
                .thenReturn(new ModelAndView());

        adminController.personView(STUDENT_ID);

        verify(student).view(STUDENT_ID, ADMIN_ROLE);
    }

    @Test
    public void personDelete() {

        when(student.delete(modelMock, STUDENT_ID, ADMIN_ROLE))
                .thenReturn("");

        adminController.personDelete(modelMock, STUDENT_ID);

        verify(student).delete(modelMock, STUDENT_ID, ADMIN_ROLE);
    }

    @Test
    public void personCreateForm() {

        when(student.createForm(modelMock, ADMIN_ROLE))
                .thenReturn("");

        adminController.personCreateForm(modelMock);

        verify(student).createForm(modelMock, ADMIN_ROLE);
    }

    @Test
    public void personCreateFormProcessing() {

        StudentCreateForm formMock = mock(StudentCreateForm.class);
        BindingResult bindingResultMock = mock(BindingResult.class);


        when(student.createFormProcessing(formMock, bindingResultMock, modelMock, ADMIN_ROLE))
                .thenReturn("");

        adminController.personCreateFormProcessing(formMock, bindingResultMock, modelMock);

        verify(student).createFormProcessing(formMock, bindingResultMock, modelMock, ADMIN_ROLE);
    }

    @Test
    public void personEditForm() {

        when(student.editForm(STUDENT_ID, ADMIN_ROLE))
                .thenReturn(new ModelAndView());

        adminController.personEditForm(STUDENT_ID);

        verify(student).editForm(STUDENT_ID, ADMIN_ROLE);
    }

    @Test
    public void personEditFormProcessing() {

        StudentCreateForm formMock = mock(StudentCreateForm.class);
        BindingResult bindingResultMock = mock(BindingResult.class);

        when(student.editFormProcessing(formMock, STUDENT_ID, bindingResultMock, modelMock, ADMIN_ROLE))
                .thenReturn("");

        adminController.personEditFormProcessing(formMock, STUDENT_ID, bindingResultMock, modelMock);

        verify(student).editFormProcessing(formMock, STUDENT_ID, bindingResultMock, modelMock, ADMIN_ROLE);
    }
}