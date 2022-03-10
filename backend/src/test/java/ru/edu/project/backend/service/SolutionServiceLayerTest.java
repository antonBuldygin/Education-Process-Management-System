package ru.edu.project.backend.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionForm;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionVerifyForm;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.da.SolutionDALayer;
import ru.edu.project.backend.model.SolutionStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class SolutionServiceLayerTest {
    public static final Long SOLUTION_ID = 111L;
    public static final Long STUDENT_ID = 222L;
    public static final Long TASK_ID = 333L;
    public static final Long GROUP_ID = 444L;
    public static final Long SOLUTION_STATUS = 1L;
    public static final Timestamp SOLUTION_CREATION_TS = new Timestamp(1L);
    public static final Timestamp SOLUTION_LAST_ACTION_TS = new Timestamp(2L);
    public static final Timestamp SOLUTION_CHECKED_TS = new Timestamp(3L);
    public static final Integer SOLUTION_SCORE = 56;
    public static final String SOLUTION_TEXT = "Text of the solution";
    public static final String SOLUTION_COMMENT = "Comment of the solution";
    public static final String STUDENT_FIRST_NAME = "Ivan";
    public static final String STUDENT_LAST_NAME = "Ivanov";
    public static final Timestamp STUDENT_BIRTHDAY_TS = new Timestamp(4L);
    public static final String STUDENT_PHONE = "89998887766";
    public static final String STUDENT_EMAIL = "ivanov@mail.ru";
    public static final Integer TASK_NUM = 678;
    public static final Timestamp TASK_START_TS = new Timestamp(5L);
    public static final Timestamp TASK_END_TS = new Timestamp(6L);
    public static final String TASK_TEXT = "Text of the task";
    public static final String TASK_TITLE = "Title of the task";
    public static final Timestamp ACTION_TIME_TS = new Timestamp(7L);
    private static final String SOLUTION_FORM_TEXT = "Solution form text";
    private static final String SOLUTION_FORM_COMMENT = "Solution form comment";

    @Mock
    SolutionDALayer daLayer;

    @Mock
    TaskServiceLayer taskService;

    @Mock
    StudentServiceLayer studentService;

    @InjectMocks
    SolutionServiceLayer solutionServiceLayer;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void getSolutionsByStudent() {
        List<SolutionInfo> solutionsList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();
        SolutionInfo solution = solutionBuilder(SolutionStatus.TASK_IN_WORK);
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        solutionsList.add(solution);

        when(daLayer.getSolutionsByStudent(STUDENT_ID)).thenReturn(solutionsList);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        List<SolutionInfo> result = solutionServiceLayer.getSolutionsByStudent(STUDENT_ID);

        assertEquals(1, result.size());

        SolutionInfo resultSolutionInfo = result.get(0);

        assertEquals(SOLUTION_ID, resultSolutionInfo.getId());
        assertEquals(STUDENT_FIRST_NAME + " " + STUDENT_LAST_NAME
                , resultSolutionInfo.getStudentName());
        assertEquals(TASK_ID, resultSolutionInfo.getTaskId());
        assertEquals(TASK_TEXT, resultSolutionInfo.getTaskText());
        assertEquals(TASK_NUM, resultSolutionInfo.getTaskNum());
        assertEquals(TASK_TITLE, resultSolutionInfo.getTaskTitle());
        assertNotNull(resultSolutionInfo.getActionHistory());

        verify(daLayer, times(1)).getSolutionsByStudent(STUDENT_ID);
    }

    @Test
    public void getSolutionByStudentAndTask() {
        List<Action> actionList = new ArrayList<>();
        SolutionInfo solution = solutionBuilder(SolutionStatus.TASK_IN_WORK);
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        when(daLayer.getSolutionByStudentAndTask(STUDENT_ID, TASK_ID)).thenReturn(solution);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        SolutionInfo result = solutionServiceLayer.getSolutionByStudentAndTask(STUDENT_ID, TASK_ID);

        assertEquals(SOLUTION_ID, result.getId());
        assertEquals(STUDENT_FIRST_NAME + " " + STUDENT_LAST_NAME
                , result.getStudentName());
        assertEquals(TASK_ID, result.getTaskId());
        assertEquals(TASK_TEXT, result.getTaskText());
        assertEquals(TASK_NUM, result.getTaskNum());
        assertEquals(TASK_TITLE, result.getTaskTitle());
        assertNotNull(result.getActionHistory());

        verify(daLayer, times(1)).getSolutionByStudentAndTask(STUDENT_ID, TASK_ID);
    }

    @Test
    public void getDetailedInfo() {
        List<Action> actionList = new ArrayList<>();
        SolutionInfo solution = solutionBuilder(SolutionStatus.TASK_IN_WORK);
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        when(daLayer.getById(SOLUTION_ID)).thenReturn(solution);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        SolutionInfo result = solutionServiceLayer.getDetailedInfo(SOLUTION_ID);

        assertEquals(SOLUTION_ID, result.getId());
        assertEquals(STUDENT_FIRST_NAME + " " + STUDENT_LAST_NAME
                , result.getStudentName());
        assertEquals(TASK_ID, result.getTaskId());
        assertEquals(TASK_TEXT, result.getTaskText());
        assertEquals(TASK_NUM, result.getTaskNum());
        assertEquals(TASK_TITLE, result.getTaskTitle());
        assertNotNull(result.getActionHistory());

        verify(daLayer, times(1)).getById(SOLUTION_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDetailedInfoWithErrors() {
        when(daLayer.getById(SOLUTION_ID)).thenReturn(null);

        SolutionInfo result = solutionServiceLayer.getDetailedInfo(SOLUTION_ID);

        verify(daLayer, times(1)).getById(SOLUTION_ID);
    }

    @Test
    public void takeToWork() {

        Action action = actionBuilder(SolutionStatus.TASK_IN_WORK, null);
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        List<Action> actionList = new ArrayList<>();
        actionList.add(action);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        when(daLayer.save(any(SolutionInfo.class))).thenAnswer(invocation -> {
            SolutionInfo solutionInfo = invocation.getArgument(0, SolutionInfo.class);
            solutionInfo.setId(SOLUTION_ID);
            return solutionInfo;
        });

        SolutionInfo result = solutionServiceLayer.takeToWork(STUDENT_ID, TASK_ID);

        assertEquals(STUDENT_FIRST_NAME + " " + STUDENT_LAST_NAME
                , result.getStudentName());
        assertEquals(STUDENT_ID, result.getStudentId());
        assertEquals(TASK_ID, result.getTaskId());
        assertEquals(TASK_TEXT, result.getTaskText());
        assertEquals(TASK_NUM, result.getTaskNum());
        assertEquals(TASK_TITLE, result.getTaskTitle());
        assertNotNull(result.getActionHistory());
        assertEquals(SolutionStatus.TASK_IN_WORK, result.getStatus());

        verify(daLayer, times(1)).save(any());
        verify(daLayer, times(1)).doAction(any(), isNull());

    }


    @Test
    public void uploadSolution() {
        SolutionInfo solution = solutionBuilder(SolutionStatus.TASK_IN_WORK);
        SolutionForm solutionForm = SolutionForm.builder()
                .solutionId(SOLUTION_ID)
                .text(SOLUTION_FORM_TEXT)
                .comment(SOLUTION_FORM_COMMENT)
                .build();

        when(daLayer.getById(SOLUTION_ID)).thenReturn(solution);

        Action action = actionBuilder(SolutionStatus.UPLOADED, SOLUTION_FORM_COMMENT);
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        List<Action> actionList = new ArrayList<>();
        actionList.add(action);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        SolutionInfo result = solutionServiceLayer.uploadSolution(solutionForm);

        assertNotNull(result.getActionHistory());
        assertEquals(SolutionStatus.UPLOADED, result.getStatus());
        assertEquals(SOLUTION_FORM_TEXT, result.getText());
        assertEquals(SOLUTION_FORM_TEXT, result.getText());

        verify(daLayer, times(1)).getById(SOLUTION_ID);
        verify(daLayer, times(1)).save(any());
        verify(daLayer, times(1)).doAction(any(), any());

    }

    @Test
    public void reUploadSolution() {
        SolutionInfo solution = solutionBuilder(SolutionStatus.UPLOADED);
        SolutionForm solutionForm = SolutionForm.builder()
                .solutionId(SOLUTION_ID)
                .text(SOLUTION_FORM_TEXT)
                .comment(SOLUTION_FORM_COMMENT)
                .build();

        when(daLayer.getById(SOLUTION_ID)).thenReturn(solution);

        Action action = actionBuilder(SolutionStatus.UPLOADED, SOLUTION_FORM_COMMENT);
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        List<Action> actionList = new ArrayList<>();
        actionList.add(action);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        SolutionInfo result = solutionServiceLayer.uploadSolution(solutionForm);

        assertNotNull(result.getActionHistory());
        assertEquals(SolutionStatus.UPLOADED, result.getStatus());
        assertEquals(SOLUTION_FORM_TEXT, result.getText());

        verify(daLayer, times(1)).getById(SOLUTION_ID);
        verify(daLayer, times(1)).save(any());
        verify(daLayer, times(1)).updateAction(any(), any());

    }

    @Test (expected = IllegalArgumentException.class)
    public void uploadSolutionWithErrors() {
        SolutionInfo solution = solutionBuilder(SolutionStatus.IN_REVIEW);
        SolutionForm solutionForm = SolutionForm.builder()
                .solutionId(SOLUTION_ID)
                .text(SOLUTION_FORM_TEXT)
                .comment(SOLUTION_FORM_COMMENT)
                .build();

        when(daLayer.getById(SOLUTION_ID)).thenReturn(solution);

        SolutionInfo result = solutionServiceLayer.uploadSolution(solutionForm);

    }

    @Test
    public void takeForReview() {
        SolutionInfo solution = solutionBuilder(SolutionStatus.UPLOADED);

        when(daLayer.getById(SOLUTION_ID)).thenReturn(solution);

        Action action = actionBuilder(SolutionStatus.IN_REVIEW, SOLUTION_FORM_COMMENT);
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        List<Action> actionList = new ArrayList<>();
        actionList.add(action);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        SolutionInfo result = solutionServiceLayer.takeForReview(SOLUTION_ID);

        assertNotNull(result.getActionHistory());
        assertEquals(SolutionStatus.IN_REVIEW, result.getStatus());

        verify(daLayer, times(1)).getById(SOLUTION_ID);
        verify(daLayer, times(1)).save(any());
        verify(daLayer, times(1)).doAction(any(), any());

    }

    @Test (expected = IllegalArgumentException.class)
    public void takeForReviewWithErrors() {
        SolutionInfo solution = solutionBuilder(SolutionStatus.TASK_IN_WORK);

        when(daLayer.getById(SOLUTION_ID)).thenReturn(solution);

        SolutionInfo result = solutionServiceLayer.takeForReview(SOLUTION_ID);
    }


    @Test
    public void verifySolution() {
        SolutionInfo solution = solutionBuilder(SolutionStatus.IN_REVIEW);
        SolutionVerifyForm solutionVerifyForm = SolutionVerifyForm.builder()
                .solutionId(SOLUTION_ID)
                .score(SOLUTION_SCORE)
                .comment(SOLUTION_FORM_COMMENT)
                .build();

        when(daLayer.getById(SOLUTION_ID)).thenReturn(solution);

        Action action = actionBuilder(SolutionStatus.CHECKED, SOLUTION_FORM_COMMENT);
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        List<Action> actionList = new ArrayList<>();
        actionList.add(action);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        SolutionInfo result = solutionServiceLayer.verify(solutionVerifyForm);

        assertNotNull(result.getActionHistory());
        assertEquals(SolutionStatus.CHECKED, result.getStatus());
        assertEquals(SOLUTION_SCORE, result.getScore().getValue());

        verify(daLayer, times(1)).getById(SOLUTION_ID);
        verify(daLayer, times(1)).save(any());
        verify(daLayer, times(1)).doAction(any(), any());

    }

    @Test (expected = IllegalArgumentException.class)
    public void verifyWithErrors() {
        SolutionVerifyForm solutionVerifyForm = SolutionVerifyForm.builder()
                .solutionId(SOLUTION_ID)
                .score(SOLUTION_SCORE)
                .comment(SOLUTION_FORM_COMMENT)
                .build();

        SolutionInfo solution = solutionBuilder(SolutionStatus.TASK_IN_WORK);

        when(daLayer.getById(SOLUTION_ID)).thenReturn(solution);

        SolutionInfo result = solutionServiceLayer.verify(solutionVerifyForm);
    }

    @Test
    public void getSolutionsByTask() {
        List<SolutionInfo> solutionsList = new ArrayList<>();
        SolutionInfo solution = solutionBuilder(SolutionStatus.TASK_IN_WORK);
        solutionsList.add(solution);

        List<Action> actionList = new ArrayList<>();
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        when(daLayer.getSolutionsByTask(TASK_ID)).thenReturn(solutionsList);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        List<SolutionInfo> result = solutionServiceLayer.getSolutionsByTask(TASK_ID);

        assertEquals(1, result.size());

        SolutionInfo resultSolutionInfo = result.get(0);

        assertEquals(SOLUTION_ID, resultSolutionInfo.getId());
        assertEquals(STUDENT_FIRST_NAME + " " + STUDENT_LAST_NAME
                , resultSolutionInfo.getStudentName());
        assertEquals(TASK_ID, resultSolutionInfo.getTaskId());
        assertEquals(TASK_TEXT, resultSolutionInfo.getTaskText());
        assertEquals(TASK_NUM, resultSolutionInfo.getTaskNum());
        assertEquals(TASK_TITLE, resultSolutionInfo.getTaskTitle());
        assertNotNull(resultSolutionInfo.getActionHistory());

        verify(daLayer, times(1)).getSolutionsByTask(TASK_ID);
    }

    @Test
    public void searchSolutions() {
        PagedView<SolutionInfo> solutionsList = mock(PagedView.class);
        SolutionSearch solutionSearch = mock(SolutionSearch.class);
        List<SolutionInfo> solutionInfoList = new ArrayList<>();
        SolutionInfo solution = solutionBuilder(SolutionStatus.TASK_IN_WORK);

        solutionInfoList.add(solution);

        List<Action> actionList = new ArrayList<>();
        StudentInfo student = studentBuilder();
        TaskInfo task = taskBuilder();

        when(daLayer.search(solutionSearch)).thenReturn(solutionsList);

        when(solutionsList.getTotal()).thenReturn(1L);
        when(solutionsList.getElements()).thenReturn(solutionInfoList);

        when(studentService.getById(eq(STUDENT_ID))).thenReturn(student);

        when(daLayer.getActionsBySolution((SOLUTION_ID))).thenReturn(actionList);

        when(taskService.getById(TASK_ID)).thenReturn(task);

        PagedView<SolutionInfo> result = solutionServiceLayer.searchSolutions(solutionSearch);

        assertEquals(1, result.getTotal());

        SolutionInfo resultSolutionInfo = result.getElements().get(0);

        assertEquals(SOLUTION_ID, resultSolutionInfo.getId());
        assertEquals(STUDENT_FIRST_NAME + " " + STUDENT_LAST_NAME
                , resultSolutionInfo.getStudentName());
        assertEquals(TASK_ID, resultSolutionInfo.getTaskId());
        assertNotNull(resultSolutionInfo.getActionHistory());

        verify(daLayer, times(1)).search(solutionSearch);

    }


    private Action actionBuilder(SolutionStatus status, String comment) {
        return Action.builder()
                .time(ACTION_TIME_TS)
                .comment(comment)
                .status(status)
                .build();
    }

    private TaskInfo taskBuilder() {
        return TaskInfo.builder()
                .id(TASK_ID)
                .num(TASK_NUM)
                .endDate(TASK_END_TS)
                .startDate(TASK_START_TS)
                .text(TASK_TEXT)
                .groupId(GROUP_ID)
                .title(TASK_TITLE)
                .build();
    }

    private StudentInfo studentBuilder() {
        return StudentInfo.builder()
                .id(STUDENT_ID)
                .firstName(STUDENT_FIRST_NAME)
                .lastName(STUDENT_LAST_NAME)
                .birthday(STUDENT_BIRTHDAY_TS)
                .phone(STUDENT_PHONE)
                .email(STUDENT_EMAIL)
                .build();
    }

    private SolutionInfo solutionBuilder(SolutionStatus status) {
        return SolutionInfo.builder()
                .id(SOLUTION_ID)
                .taskId(TASK_ID)
                .studentId(STUDENT_ID)
                .status(status)
                .creationTime(SOLUTION_CREATION_TS)
                .build();
    }
}