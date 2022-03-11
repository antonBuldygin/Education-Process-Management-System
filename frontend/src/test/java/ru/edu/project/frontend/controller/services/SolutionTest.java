package ru.edu.project.frontend.controller.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.authorization.UserDetailsId;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionForm;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.solutions.SolutionVerifyForm;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.api.tasks.TaskService;
import ru.edu.project.frontend.controller.forms.solutions.SolutionCreateForm;
import ru.edu.project.frontend.controller.forms.solutions.SolutionUploadForm;
import ru.edu.project.frontend.controller.forms.solutions.SolutionVerifyingForm;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class SolutionTest {

    public static final Long STUDENT_ID = 111L;

    public static final Long GROUP_ID = 222L;

    public static final Long SOLUTION_ID = 333L;

    public static final Long TASK_ID = 444L;

    public static final Integer TASK_NUM = 123;

    public static final String TEACHER_ROLE = "TEACHER";
    public static final String STUDENT_ROLE = "STUDENT";
    public static final String SOLUTION_TEXT = "Text of the solution";
    public static final String SOLUTION_COMMENT = "Comment of the solution";
    public static final Integer SOLUTION_SCORE = 50;

    @Mock
    private UserDetailsId detailsId;

    @Mock
    private SolutionService solutionService;

    @Mock
    private TaskService taskService;

    @Mock
    private StudentService studentService;

    @Mock
    private Authentication authentication;

    @Mock
    SolutionSearch solutionSearch;

    @Mock
    private Model modelMock;


    @InjectMocks
    private Solution solution;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
        when(authentication.getPrincipal()).thenReturn(detailsId);
        when(detailsId.getUserId()).thenReturn(STUDENT_ID);
    }

    @Test
    public void indexAll() {

        String searchBy = "";
        boolean isAsc = true;
        int page = 1;
        int perPage = 10;

        Solution.SearchFields searchByField = Solution.SearchFields.byString(searchBy);

        PagedView<SolutionInfo> solutionPagedView = PagedView.<SolutionInfo>builder().build();

        when(solutionService.searchSolutions(any())).thenReturn(solutionPagedView);

        ModelAndView view = solution.index(searchBy, isAsc, page, perPage, "all", TEACHER_ROLE);

        assertEquals("solution/index", view.getViewName());

        assertEquals(TEACHER_ROLE, view.getModel().get("role"));
        assertEquals(solutionPagedView, view.getModel().get("solutionsPage"));
        assertEquals(isAsc, view.getModel().get("isAsc"));
    }

    @Test
    public void indexByStudent() {

        String searchBy = "";
        boolean isAsc = true;
        int page = 1;
        int perPage = 10;

        Solution.SearchFields searchByField = Solution.SearchFields.byString(searchBy);

        PagedView<SolutionInfo> solutionPagedView = PagedView.<SolutionInfo>builder().build();

        when(solutionService.searchSolutions(any())).thenReturn(solutionPagedView);

        ModelAndView view = solution.index(searchBy, isAsc, page, perPage, String.valueOf(STUDENT_ID), TEACHER_ROLE);

        assertEquals("solution/index", view.getViewName());

        assertEquals(TEACHER_ROLE, view.getModel().get("role"));
        assertEquals(solutionPagedView, view.getModel().get("solutionsPage"));
        assertEquals(isAsc, view.getModel().get("isAsc"));

    }

    @Test
    public void view() {

        SolutionInfo solutionInfo = SolutionInfo.builder().build();

        when(solutionService.getDetailedInfo(SOLUTION_ID)).thenReturn(solutionInfo);

        ModelAndView view = solution.view(SOLUTION_ID, TEACHER_ROLE);

        assertEquals("solution/view", view.getViewName());
        assertEquals(solutionInfo, view.getModel().get("record"));
        assertEquals(TEACHER_ROLE, view.getModel().get("role"));

    }

    @Test
    public void viewError() {
        when(solutionService.getDetailedInfo(SOLUTION_ID)).thenReturn(null);

        ModelAndView view = solution.view(SOLUTION_ID, TEACHER_ROLE);

        assertEquals("solution/viewNotFound", view.getViewName());
        assertEquals(HttpStatus.NOT_FOUND, view.getStatus());
    }

    @Test
    public void createForm() {

        StudentInfo studentInfo = StudentInfo.builder().groupId(GROUP_ID).build();

        when(studentService.getById(STUDENT_ID)).thenReturn(studentInfo);

        List<TaskInfo> taskInfoList = new ArrayList<>();

        when(taskService.getTasksByGroup(GROUP_ID)).thenReturn(taskInfoList);

        String viewName = solution.createForm(modelMock, STUDENT_ID, STUDENT_ROLE);

        assertEquals("solution/create", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);

        verify(modelMock).addAttribute("tasks", taskInfoList);

    }

    @Test
    public void createFormProcessing() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        SolutionCreateForm createFormMock = mock(SolutionCreateForm.class);
        when(createFormMock.getTask()).thenReturn(TASK_ID);

        SolutionInfo expectedInfo = SolutionInfo.builder().id(SOLUTION_ID).build();

        when(solutionService.takeToWork(STUDENT_ID, TASK_ID)).thenReturn(expectedInfo);

        String viewName = solution.createFormProcessing(createFormMock, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE);

        assertEquals("redirect:/" + STUDENT_ROLE + "/solution/?created=" + SOLUTION_ID, viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);
        verify(solutionService).takeToWork(STUDENT_ID, TASK_ID);
    }

    @Test
    public void createFormProcessingHasErrors() {

        List<ObjectError> mockErrors = new ArrayList<>();
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(true);
        when(bindingResultMock.getAllErrors()).thenReturn(mockErrors);

        StudentInfo studentInfo = StudentInfo.builder().groupId(GROUP_ID).build();

        when(studentService.getById(STUDENT_ID)).thenReturn(studentInfo);

        List<TaskInfo> taskInfoList = new ArrayList<>();

        when(taskService.getTasksByGroup(GROUP_ID)).thenReturn(taskInfoList);

        SolutionCreateForm createFormMock = mock(SolutionCreateForm.class);

        String viewName = solution.createFormProcessing(createFormMock, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE);

        assertEquals("solution/create", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);

        verify(modelMock).addAttribute("tasks", taskInfoList);

        verify(modelMock).addAttribute("errorsList", mockErrors);
    }


    @Test
    public void uploadForm() {

        ModelAndView view = solution.uploadForm(SOLUTION_ID, TASK_NUM, STUDENT_ROLE);

        assertEquals("solution/upload", view.getViewName());

        assertEquals(TASK_NUM, view.getModel().get("taskNum"));

        assertEquals(STUDENT_ROLE, view.getModel().get("role"));

    }


    @Test
    public void uploadFormProcessing() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        SolutionInfo expectedInfo = SolutionInfo.builder().id(SOLUTION_ID).build();

        SolutionUploadForm uploadForm = new SolutionUploadForm();
        uploadForm.setText(SOLUTION_TEXT);
        uploadForm.setComment(SOLUTION_COMMENT);


        when(solutionService.uploadSolution(any(SolutionForm.class))).thenAnswer(invocation -> {
            SolutionForm form = invocation.getArgument(0, SolutionForm.class);
            assertEquals(SOLUTION_ID, form.getSolutionId());
            assertEquals(STUDENT_ID, form.getStudentId());
            assertEquals(SOLUTION_TEXT, form.getText());
            assertEquals(SOLUTION_COMMENT, form.getComment());
            return expectedInfo;
        });


        String viewName = solution.uploadFormProcessing(uploadForm, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE);

        assertEquals("redirect:/" + STUDENT_ROLE + "/solution/view/" + expectedInfo.getId(), viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);

        verify(solutionService).uploadSolution(any(SolutionForm.class));

    }

    @Test
    public void uploadFormProcessingHasErrors() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(true);

        List<ObjectError> mockErrors = new ArrayList<>();
        when(bindingResultMock.getAllErrors()).thenReturn(mockErrors);

        SolutionUploadForm uploadForm = new SolutionUploadForm();

        String viewName = solution.uploadFormProcessing(uploadForm, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE);

        assertEquals("solution/upload", viewName);

        verify(modelMock).addAttribute("errorsList", mockErrors);

    }

        @Test
    public void reuploadForm() {

        SolutionInfo solutionInfo = SolutionInfo.builder().build();

        when(solutionService.getDetailedInfo(SOLUTION_ID)).thenReturn(solutionInfo);

        ModelAndView view = solution.reuploadForm(SOLUTION_ID, TASK_NUM, STUDENT_ROLE);

        assertEquals("solution/upload", view.getViewName());

        assertEquals(TASK_NUM, view.getModel().get("taskNum"));

        assertEquals(solutionInfo, view.getModel().get("record"));

        assertEquals(STUDENT_ROLE, view.getModel().get("role"));

    }

    @Test
    public void reuploadFormProcessing() {

        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        SolutionInfo expectedInfo = SolutionInfo.builder().id(SOLUTION_ID).build();

        SolutionUploadForm uploadForm = new SolutionUploadForm();
        uploadForm.setText(SOLUTION_TEXT);
        uploadForm.setComment(SOLUTION_COMMENT);


        when(solutionService.uploadSolution(any(SolutionForm.class))).thenAnswer(invocation -> {
            SolutionForm form = invocation.getArgument(0, SolutionForm.class);
            assertEquals(SOLUTION_ID, form.getSolutionId());
            assertEquals(STUDENT_ID, form.getStudentId());
            assertEquals(SOLUTION_TEXT, form.getText());
            assertEquals(SOLUTION_COMMENT, form.getComment());
            return expectedInfo;
        });


        String viewName = solution.reuploadFormProcessing(uploadForm, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, STUDENT_ID, STUDENT_ROLE);

        assertEquals("redirect:/" + STUDENT_ROLE + "/solution/view/" + expectedInfo.getId(), viewName);

        verify(modelMock, times(1)).addAttribute("role", STUDENT_ROLE);

        verify(solutionService).uploadSolution(any(SolutionForm.class));
    }


    @Test
    public void reviewForm() {
        String viewName = solution.reviewForm(modelMock, SOLUTION_ID, TEACHER_ROLE);

        assertEquals("redirect:/" + TEACHER_ROLE + "/solution/view/" + SOLUTION_ID, viewName);

        verify(solutionService).takeForReview(SOLUTION_ID);

        verify(modelMock, times(1)).addAttribute("role", TEACHER_ROLE);
    }

    @Test
    public void verifyForm() {

        ModelAndView view = solution.verifyForm(SOLUTION_ID, TASK_NUM, TEACHER_ROLE);

        assertEquals("solution/verify", view.getViewName());

        assertEquals(TEACHER_ROLE, view.getModel().get("role"));

        assertEquals(TASK_NUM, view.getModel().get("taskNum"));
    }

    @Test
    public void verifyFormProcessing() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        SolutionInfo expectedInfo = SolutionInfo.builder().id(SOLUTION_ID).build();

        SolutionVerifyingForm verifyForm = new SolutionVerifyingForm();
        verifyForm.setScore(SOLUTION_SCORE);
        verifyForm.setComment(SOLUTION_COMMENT);

        when(solutionService.verify(any(SolutionVerifyForm.class))).thenAnswer(invocation -> {
            SolutionVerifyForm form = invocation.getArgument(0, SolutionVerifyForm.class);
            assertEquals(SOLUTION_ID, form.getSolutionId());
            assertEquals(SOLUTION_COMMENT, form.getComment());
            assertEquals(SOLUTION_SCORE, form.getScore());
            return expectedInfo;
        });


        String viewName = solution.verifyFormProcessing(verifyForm, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, TEACHER_ROLE);

        assertEquals("redirect:/" + TEACHER_ROLE + "/solution/view/" + expectedInfo.getId(), viewName);

        verify(modelMock, times(1)).addAttribute("role", TEACHER_ROLE);

        verify(solutionService).verify(any(SolutionVerifyForm.class));

    }

    @Test
    public void verifyFormProcessingHasErrors() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(true);

        List<ObjectError> mockErrors = new ArrayList<>();
        when(bindingResultMock.getAllErrors()).thenReturn(mockErrors);

        SolutionVerifyingForm verifyForm = new SolutionVerifyingForm();

        String viewName = solution.verifyFormProcessing(verifyForm, SOLUTION_ID, TASK_NUM, bindingResultMock, modelMock, TEACHER_ROLE);

        assertEquals("solution/verify", viewName);

        verify(modelMock).addAttribute("errorsList", mockErrors);
    }

    @Test
    public void searchFields() {
        String searchBy = "TASK_NUM";

        Solution.SearchFields searchByField = Solution.SearchFields.byString(searchBy);

        assertEquals("TASK_NUM", searchByField);
    }
}