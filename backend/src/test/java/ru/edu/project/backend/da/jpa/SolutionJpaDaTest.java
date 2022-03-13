package ru.edu.project.backend.da.jpa;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.da.jpa.converter.SolutionActionMapper;
import ru.edu.project.backend.da.jpa.converter.SolutionInfoMapper;
import ru.edu.project.backend.da.jpa.entity.SolutionActionEntity;
import ru.edu.project.backend.da.jpa.entity.SolutionEntity;
import ru.edu.project.backend.da.jpa.repository.SolutionActionEntityRepository;
import ru.edu.project.backend.da.jpa.repository.SolutionEntityRepository;
import ru.edu.project.backend.model.SolutionStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class SolutionJpaDaTest {

    public static final Long STUDENT_ID = 111L;

    public static final Long TASK_ID = 222L;

    public static final Long SOLUTION_ID = 333L;

    public static final String ACTION_COMMENT = "Comment";

    public static final Timestamp SOLUTION_LAST_ACTION_TS = new Timestamp(1L);



    @Mock
    SolutionEntityRepository repo;

    @Mock
    SolutionInfoMapper mapper;

    @Mock
    SolutionActionMapper linkMapper;

    @Mock
    SolutionActionEntityRepository linkRepo;

    @InjectMocks
    SolutionJpaDa solutionJpaDa;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void getSolutionsByStudent() {
        List<SolutionEntity> solutionEntities = new ArrayList<>();
        List<SolutionInfo> solutionInfos = new ArrayList<>();

        when(repo.findByStudentId(STUDENT_ID)).thenReturn(solutionEntities);

        when(mapper.mapList(solutionEntities)).thenReturn(solutionInfos);

        List<SolutionInfo> result = solutionJpaDa.getSolutionsByStudent(STUDENT_ID);

        assertNotNull(result);

        verify(repo, times(1)).findByStudentId(STUDENT_ID);
        verify(mapper,times(1)).mapList(solutionEntities);

    }

    @Test
    public void searchAll() {

        SolutionSearch solutionSearch = SolutionSearch.builder()
                .asc(true)
                .page(1)
                .perPage(10)
                .orderBy("ID")
                .build();

        Page<SolutionEntity> page = mock(Page.class);

        List<SolutionInfo> solutionInfos = new ArrayList<>();

        when(repo.findAll(any(PageRequest.class))).thenReturn(page);

        when(mapper.mapList(anyList())).thenReturn(solutionInfos);

        when(page.getTotalElements()).thenReturn(1L);

        when(page.getTotalPages()).thenReturn(1);

        PagedView<SolutionInfo> solutionInfoPagedView = solutionJpaDa.search(solutionSearch);

        verify(repo, times(1)).findAll(any(PageRequest.class));

    }

    @Test
    public void searchByStudent() {

        SolutionSearch solutionSearch = SolutionSearch.builder()
                .studentId(STUDENT_ID)
                .asc(true)
                .page(1)
                .perPage(10)
                .orderBy("ID")
                .build();

        Page<SolutionEntity> page = mock(Page.class);

        List<SolutionInfo> solutionInfos = new ArrayList<>();

        when(repo.findByStudentId(eq(STUDENT_ID), any(PageRequest.class))).thenReturn(page);

        when(mapper.mapList(anyList())).thenReturn(solutionInfos);

        when(page.getTotalElements()).thenReturn(1L);

        when(page.getTotalPages()).thenReturn(1);

        PagedView<SolutionInfo> solutionInfoPagedView = solutionJpaDa.search(solutionSearch);

        verify(repo, times(1)).findByStudentId(eq(STUDENT_ID), any(PageRequest.class));

    }

    @Test
    public void getSolutionByStudentAndTask() {
        SolutionEntity solutionEntity = mock(SolutionEntity.class);
        SolutionInfo solutionInfo = mock(SolutionInfo.class);

        when(repo.findByStudentIdAndTaskId(STUDENT_ID, TASK_ID)).thenReturn(solutionEntity);

        when(mapper.map(any(SolutionEntity.class))).thenReturn(solutionInfo);

        SolutionInfo result = solutionJpaDa.getSolutionByStudentAndTask(STUDENT_ID, TASK_ID);

        assertNotNull(result);

        verify(repo, times(1)).findByStudentIdAndTaskId(STUDENT_ID, TASK_ID);

        verify(mapper, times(1)).map(any(SolutionEntity.class));

    }

    @Test
    public void getSolutionsByTask() {
        List<SolutionEntity> solutionEntities = new ArrayList<>();
        List<SolutionInfo> solutionInfos = new ArrayList<>();

        when(repo.findByTaskId(TASK_ID)).thenReturn(solutionEntities);

        when(mapper.mapList(solutionEntities)).thenReturn(solutionInfos);

        List<SolutionInfo> result = solutionJpaDa.getSolutionsByTask(TASK_ID);

        assertNotNull(result);

        verify(repo, times(1)).findByTaskId(TASK_ID);
        verify(mapper,times(1)).mapList(solutionEntities);

    }


    @Test
    public void getById() {

        SolutionEntity solutionEntity = mock(SolutionEntity.class);
        Optional<SolutionEntity> optionalSolutionEntity = Optional.of(solutionEntity);

        SolutionInfo solutionInfo = mock(SolutionInfo.class);

        when(repo.findById(SOLUTION_ID)).thenReturn(optionalSolutionEntity);

        when(mapper.map(any(SolutionEntity.class))).thenReturn(solutionInfo);

        SolutionInfo result = solutionJpaDa.getById(SOLUTION_ID);

        verify(repo).findById(SOLUTION_ID);

        verify(mapper, times(1)).map(any(SolutionEntity.class));

    }

    @Test
    public void save() {
        SolutionInfo solutionInfo = SolutionInfo.builder().build();
        SolutionEntity solutionEntity = mock(SolutionEntity.class);

        when(mapper.map(any(SolutionInfo.class))).thenReturn(solutionEntity);

        when(repo.save(solutionEntity)).thenAnswer(invocation -> {
            SolutionEntity solution = new SolutionEntity();
            solution.setId(SOLUTION_ID);
            return solution;
        });

        when(mapper.map(any(SolutionEntity.class))).thenAnswer(invocationOnMock -> {
            SolutionEntity entity = invocationOnMock.getArgument(0, SolutionEntity.class);
            SolutionInfo solution = SolutionInfo.builder().id(entity.getId()).build();
            return solution;
        });

        SolutionInfo result = solutionJpaDa.save(solutionInfo);

        assertEquals(SOLUTION_ID, result.getId());

        verify(mapper, times(1)).map(any(SolutionInfo.class));

        verify(repo, times(1)).save(solutionEntity);

    }

    @Test
    public void doAction() {
        SolutionInfo solutionInfo = SolutionInfo.builder()
                .id(SOLUTION_ID)
                .status(SolutionStatus.TASK_IN_WORK)
                .lastActionTime(SOLUTION_LAST_ACTION_TS)
                .build();

        solutionJpaDa.doAction(solutionInfo, ACTION_COMMENT);

        verify(linkRepo, times(1)).save(any(SolutionActionEntity.class));
    }

    @Test
    public void getActionsBySolution() {
        List<SolutionActionEntity> solutionActionEntities = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();

        when(linkRepo.findAllByPkSolutionId(SOLUTION_ID)).thenReturn(solutionActionEntities);

        when(linkMapper.mapList(solutionActionEntities)).thenReturn(actionList);

        List<Action> result = solutionJpaDa.getActionsBySolution(SOLUTION_ID);

        assertEquals(actionList, result);

        verify(linkRepo, times(1)).findAllByPkSolutionId(SOLUTION_ID);
        verify(linkMapper, times(1)).mapList(solutionActionEntities);
    }

    @Test
    public void updateAction() {

        SolutionInfo solutionInfo = mock(SolutionInfo.class);
        SolutionActionEntity entity = new SolutionActionEntity();

        when(linkMapper.map(solutionInfo)).thenReturn(entity);

        when(linkRepo.save(entity)).thenReturn(entity);

        solutionJpaDa.updateAction(solutionInfo, ACTION_COMMENT);

        verify(linkMapper, times(1)).map(solutionInfo);

        verify(linkRepo, times(1)).save(entity);

    }

    @Test
    public void getAllSolutions() {
        List<SolutionEntity> solutionEntities = new ArrayList<>();
        List<SolutionInfo> solutionInfos = new ArrayList<>();

        when(repo.findAll()).thenReturn(solutionEntities);

        when(mapper.map(solutionEntities)).thenReturn(solutionInfos);

        List<SolutionInfo> result = solutionJpaDa.getAllSolutions();

        assertNotNull(result);

        verify(repo, times(1)).findAll();
        verify(mapper,times(1)).map(solutionEntities);

    }
}