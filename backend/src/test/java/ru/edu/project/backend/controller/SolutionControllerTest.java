package ru.edu.project.backend.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionForm;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionVerifyForm;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.service.SolutionServiceLayer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class SolutionControllerTest {

    public static final long STUDENT_ID = 1L;
    public static final long TASK_ID = 2L;
    public static final long SOLUTION_ID = 3L;

    @Mock
    SolutionServiceLayer delegate;

    @InjectMocks
    SolutionController solutionController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void getSolutionsByStudent() {

        List<SolutionInfo> solutionInfos = new ArrayList<>();

        when(delegate.getSolutionsByStudent(STUDENT_ID)).thenReturn(solutionInfos);

        List<SolutionInfo> result = solutionController.getSolutionsByStudent(STUDENT_ID);

        Mockito.verify(delegate, times(1)).getSolutionsByStudent(STUDENT_ID);
    }

    @Test
    public void getSolutionsByTask() {
        List<SolutionInfo> solutionInfos = new ArrayList<>();

        when(delegate.getSolutionsByTask(TASK_ID)).thenReturn(solutionInfos);

        List<SolutionInfo> result = solutionController.getSolutionsByTask(TASK_ID);

        Mockito.verify(delegate, times(1)).getSolutionsByTask(TASK_ID);
    }

    @Test
    public void searchSolutions() {
        PagedView<SolutionInfo> solutionInfoPagedView = mock(PagedView.class);
        SolutionSearch solutionSearch = mock(SolutionSearch.class);

        when(delegate.searchSolutions(solutionSearch)).thenReturn(solutionInfoPagedView);

        PagedView<SolutionInfo> result = solutionController.searchSolutions(solutionSearch);

        Mockito.verify(delegate, times(1)).searchSolutions(solutionSearch);
    }

    @Test
    public void getSolutionByStudentAndTask() {

        SolutionInfo solutionInfo = mock(SolutionInfo.class);

        when(delegate.getSolutionByStudentAndTask(STUDENT_ID, TASK_ID)).thenReturn(solutionInfo);

        SolutionInfo result = solutionController.getSolutionByStudentAndTask(STUDENT_ID, TASK_ID);

        assertEquals(solutionInfo, result);

        Mockito.verify(delegate, times(1)).getSolutionByStudentAndTask(STUDENT_ID, TASK_ID);
    }

    @Test
    public void getDetailedInfo() {

        SolutionInfo solutionInfo = mock(SolutionInfo.class);

        when(delegate.getDetailedInfo(SOLUTION_ID)).thenReturn(solutionInfo);

        SolutionInfo result = solutionController.getDetailedInfo(SOLUTION_ID);

        assertEquals(solutionInfo, result);

        Mockito.verify(delegate, times(1)).getDetailedInfo(SOLUTION_ID);
    }

    @Test
    public void takeToWork() {

        SolutionInfo solutionInfo = mock(SolutionInfo.class);

        when(delegate.takeToWork(STUDENT_ID, TASK_ID)).thenReturn(solutionInfo);

        SolutionInfo result = solutionController.takeToWork(STUDENT_ID, TASK_ID);

        assertEquals(solutionInfo, result);

        Mockito.verify(delegate, times(1)).takeToWork(STUDENT_ID, TASK_ID);
    }

    @Test
    public void uploadSolution() {

        SolutionInfo solutionInfo = mock(SolutionInfo.class);
        SolutionForm solutionForm = mock(SolutionForm.class);

        when(delegate.uploadSolution(solutionForm)).thenReturn(solutionInfo);

        SolutionInfo result = solutionController.uploadSolution(solutionForm);

        assertEquals(solutionInfo, result);

        Mockito.verify(delegate, times(1)).uploadSolution(solutionForm);
    }

    @Test
    public void takeForReview() {

        SolutionInfo solutionInfo = mock(SolutionInfo.class);

        when(delegate.takeForReview(SOLUTION_ID)).thenReturn(solutionInfo);

        SolutionInfo result = solutionController.takeForReview(SOLUTION_ID);

        assertEquals(solutionInfo, result);

        Mockito.verify(delegate, times(1)).takeForReview(SOLUTION_ID);
    }

    @Test
    public void verify() {

        SolutionInfo solutionInfo = mock(SolutionInfo.class);
        SolutionVerifyForm solutionVerifyForm = mock(SolutionVerifyForm.class);

        when(delegate.verify(solutionVerifyForm)).thenReturn(solutionInfo);

        SolutionInfo result = solutionController.verify(solutionVerifyForm);

        assertEquals(solutionInfo, result);

        Mockito.verify(delegate, times(1)).verify(solutionVerifyForm);
    }
}