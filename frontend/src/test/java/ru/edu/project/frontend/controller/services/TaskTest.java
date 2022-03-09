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
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.tasks.TaskForm;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.api.tasks.TaskService;
import ru.edu.project.frontend.controller.forms.tasks.TaskCreateForm;

import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class TaskTest {

    public static final Long STUDENT_ID = 111L;
    public static final Long GROUP_ID = 222L;
    public static final Long TASK_ID = 333L;
    public static final String STUDENT_ROLE = "student";
    public static final String TASK_TITLE = "Title";
    public static final String TASK_TEXT = "Text";
    public static final Timestamp TASK_DATE = new Timestamp(new Date().getTime());
    public static final Integer TASK_NUM = 123;

    @Mock
    private UserDetailsId detailsId;

    @Mock
    private TaskService taskService;

    @Mock
    private GroupsService groupService;

    @Mock
    private SolutionService solutionService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model modelMock;


    @InjectMocks
    private Task task;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
        when(authentication.getPrincipal()).thenReturn(detailsId);
        when(detailsId.getUserId()).thenReturn(STUDENT_ID);
    }

    @Test
    public void indexAll() {

        List<TaskInfo> taskList = new ArrayList<>();

        when(taskService.getAllTasks()).thenReturn(taskList);

        String viewName = task.index(modelMock, "all", STUDENT_ROLE);

        assertEquals("task/index", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);
        verify(modelMock).addAttribute("groupId", "all");
        verify(modelMock).addAttribute("tasks", taskList);
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void indexByGroup() {

        List<TaskInfo> taskList = new ArrayList<>();

        when(taskService.getTasksByGroup(GROUP_ID)).thenReturn(taskList);

        String viewName = task.index(modelMock, String.valueOf(GROUP_ID), STUDENT_ROLE);

        assertEquals("task/index", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);
        verify(modelMock).addAttribute("groupId", String.valueOf(GROUP_ID));
        verify(modelMock).addAttribute("tasks", taskList);
        verify(taskService, times(1)).getTasksByGroup(GROUP_ID);
    }


    @Test
    public void view() {

        TaskInfo expectedInfo = TaskInfo.builder().build();

        when(taskService.getById(TASK_ID)).thenReturn(expectedInfo);

        ModelAndView view = task.view(TASK_ID, STUDENT_ROLE);

        assertEquals("task/view", view.getViewName());
        assertEquals(expectedInfo, view.getModel().get("record"));
        assertEquals(STUDENT_ROLE, view.getModel().get("role"));

    }

    @Test
    public void delete() {

        when(solutionService.getSolutionsByTask(TASK_ID)).thenReturn(null);

        String viewName = task.delete(modelMock, TASK_ID, STUDENT_ROLE);

        assertEquals("redirect:/" + STUDENT_ROLE + "/task/all", viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);

        verify(taskService, times(1)).deleteById(TASK_ID);

    }

    @Test
    public void deleteHasErrors() {

        List<SolutionInfo> solutionsByTask = new ArrayList<>();

        solutionsByTask.add(SolutionInfo.builder().build());

        when(solutionService.getSolutionsByTask(TASK_ID)).thenReturn(solutionsByTask);

        String viewName = task.delete(modelMock, TASK_ID, STUDENT_ROLE);

        assertEquals("task/deleteError", viewName);

        verify(modelMock, times(0)).addAttribute(anyString());

    }

    @Test
    public void createForm() {

        List<GroupInfo> groupInfoList = new ArrayList<>();

        when(groupService.getAllGroupsInfo()).thenReturn(groupInfoList);

        ModelAndView view = task.createForm(STUDENT_ROLE);

        assertEquals("task/create", view.getViewName());

        assertEquals(STUDENT_ROLE, view.getModel().get("role"));
        assertEquals(groupInfoList, view.getModel().get("groups"));
    }

    @Test
    public void createFormProcessing() {
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        TaskCreateForm taskCreateForm = mock(TaskCreateForm.class);
        when(taskCreateForm.getGroupId()).thenReturn(GROUP_ID);
        when(taskCreateForm.getTitle()).thenReturn(TASK_TITLE);
        when(taskCreateForm.getText()).thenReturn(TASK_TEXT);
        when(taskCreateForm.getEndDate()).thenReturn(TASK_DATE);
        when(taskCreateForm.getStartDate()).thenReturn(TASK_DATE);
        when(taskCreateForm.getNum()).thenReturn(TASK_NUM);

        TaskInfo expectedInfo = TaskInfo.builder().id(TASK_ID).build();

        when(taskService.createTask(any(TaskForm.class))).thenAnswer(invocation -> {
            TaskForm form = invocation.getArgument(0, TaskForm.class);
            assertEquals(GROUP_ID, form.getGroupId());
            assertEquals(taskCreateForm.getTitle(), form.getTitle());
            assertEquals(taskCreateForm.getEndDate(), form.getEndDate());
            assertEquals(taskCreateForm.getStartDate(), form.getStartDate());
            assertEquals(taskCreateForm.getText(), form.getText());
            assertEquals(taskCreateForm.getNum(), form.getNum());

            return expectedInfo;
        });

        String viewName = task.createFormProcessing(taskCreateForm, bindingResultMock, modelMock, STUDENT_ROLE);

        assertEquals("redirect:/" + STUDENT_ROLE + "/task/all/?created=" + expectedInfo.getId(), viewName);

        verify(modelMock).addAttribute("role", STUDENT_ROLE);
        verify(taskService).createTask(any(TaskForm.class));
    }

    @Test
    public void createFormProcessingHasErrors() {

        List<ObjectError> mockErrors = new ArrayList<>();
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(true);
        when(bindingResultMock.getAllErrors()).thenReturn(mockErrors);

        TaskCreateForm createFormMock = mock(TaskCreateForm.class);

        String viewName = task.createFormProcessing(createFormMock, bindingResultMock, modelMock, STUDENT_ROLE);

        assertEquals("task/create", viewName);

        verify(modelMock).addAttribute("errorsList", mockErrors);

    }


    @Test
    public void editForm() {

        TaskInfo taskInfo = TaskInfo.builder().build();

        when(taskService.getById(TASK_ID)).thenReturn(taskInfo);

        List<GroupInfo> groupInfoList = new ArrayList<>();

        groupInfoList.add(GroupInfo.builder().build());

        when(groupService.getAllGroupsInfo()).thenReturn(groupInfoList);

        ModelAndView view = task.editForm(TASK_ID, STUDENT_ROLE);

        assertEquals("task/edit", view.getViewName());

        assertEquals(taskInfo, view.getModel().get("record"));

        assertEquals(STUDENT_ROLE, view.getModel().get("role"));

        assertEquals(groupInfoList, view.getModel().get("groups"));

    }

    @Test
    public void editFormProcessing() {

        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(false);

        TaskInfo expectedInfo = TaskInfo.builder().id(STUDENT_ID).build();

        TaskCreateForm taskCreateForm = mock(TaskCreateForm.class);

        when(taskCreateForm.getGroupId()).thenReturn(GROUP_ID);
        when(taskCreateForm.getTitle()).thenReturn(TASK_TITLE);
        when(taskCreateForm.getText()).thenReturn(TASK_TEXT);
        when(taskCreateForm.getEndDate()).thenReturn(TASK_DATE);
        when(taskCreateForm.getStartDate()).thenReturn(TASK_DATE);
        when(taskCreateForm.getNum()).thenReturn(TASK_NUM);

        when(taskService.editTask(any(TaskForm.class))).thenAnswer(invocation -> {
            TaskForm form = invocation.getArgument(0, TaskForm.class);
            assertEquals(GROUP_ID, form.getGroupId());
            assertEquals(taskCreateForm.getTitle(), form.getTitle());
            assertEquals(taskCreateForm.getEndDate(), form.getEndDate());
            assertEquals(taskCreateForm.getStartDate(), form.getStartDate());
            assertEquals(taskCreateForm.getText(), form.getText());
            assertEquals(taskCreateForm.getNum(), form.getNum());

            return expectedInfo;
        });


        String viewName = task.editFormProcessing(taskCreateForm, STUDENT_ID, bindingResultMock, modelMock, STUDENT_ROLE);

        assertEquals("redirect:/" + STUDENT_ROLE + "/task/view/" + expectedInfo.getId(), viewName);

        verify(modelMock, times(1)).addAttribute("role", STUDENT_ROLE);

        verify(taskService).editTask(any(TaskForm.class));
    }

    @Test
    public void editFormProcessingHasErrors() {

        List<ObjectError> mockErrors = new ArrayList<>();
        BindingResult bindingResultMock = mock(BindingResult.class);
        when(bindingResultMock.hasErrors()).thenReturn(true);
        when(bindingResultMock.getAllErrors()).thenReturn(mockErrors);

        TaskCreateForm createFormMock = mock(TaskCreateForm.class);

        String viewName = task.editFormProcessing(createFormMock, STUDENT_ID, bindingResultMock, modelMock, STUDENT_ROLE);

        assertEquals("task/create", viewName);

        verify(modelMock).addAttribute("errorsList", mockErrors);
    }

}