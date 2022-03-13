package ru.edu.project.backend.da.jpa.converter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.da.jpa.entity.SolutionActionEntity;
import ru.edu.project.backend.model.SolutionStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class SolutionActionMapperTest {

    public static final Timestamp SOLUTION_LAST_ACTION_TS = new Timestamp(1L);
    public static final Long SOLUTION_ID = 111L;
    public static final String COMMENT = "Comment";
    public static final Long ACTION_TYPE_ID = 1L;

    SolutionActionMapper solutionActionMapper;

    @Before
    public void setUp() throws Exception {
        solutionActionMapper = new SolutionActionMapperImpl();
    }

    @Test
    public void infoToEntity() {

        SolutionInfo solutionInfo = SolutionInfo.builder()
                .id(SOLUTION_ID)
                .status(SolutionStatus.TASK_IN_WORK)
                .lastActionTime(SOLUTION_LAST_ACTION_TS)
                .build();

        SolutionActionEntity solutionActionEntity = solutionActionMapper.map(solutionInfo);

        assertEquals(SOLUTION_ID, solutionActionEntity.getPk().getSolutionId());
        assertEquals(SolutionStatus.TASK_IN_WORK.getCode(), solutionActionEntity.getPk().getActionTypeId());
        assertEquals(SOLUTION_LAST_ACTION_TS, solutionActionEntity.getActionTime());
    }

    @Test
    public void infoToEntityNull() {

        SolutionInfo solutionInfo = null;

        SolutionActionEntity solutionActionEntity = solutionActionMapper.map(solutionInfo);

        assertNull(solutionActionEntity);
    }

    @Test
    public void entityToAction() {

        SolutionActionEntity solutionActionEntity = new SolutionActionEntity();

        solutionActionEntity.setComment(COMMENT);
        solutionActionEntity.setActionTime(SOLUTION_LAST_ACTION_TS);
        solutionActionEntity.setPk(SolutionActionEntity.pk(SOLUTION_ID, ACTION_TYPE_ID));

        Action result = solutionActionMapper.map(solutionActionEntity);

        assertEquals(COMMENT, result.getComment());
        assertEquals(ACTION_TYPE_ID, result.getStatus().getCode());
        assertEquals(SOLUTION_LAST_ACTION_TS, result.getTime());
    }

    @Test
    public void entityToActionNull() {

        SolutionActionEntity solutionActionEntity = null;

        Action result = solutionActionMapper.map(solutionActionEntity);

        assertNull(result);
    }

    @Test
    public void listEntityToAction() {

        SolutionActionEntity solutionActionEntity = new SolutionActionEntity();

        solutionActionEntity.setComment(COMMENT);
        solutionActionEntity.setActionTime(SOLUTION_LAST_ACTION_TS);
        solutionActionEntity.setPk(SolutionActionEntity.pk(SOLUTION_ID, ACTION_TYPE_ID));

        List<SolutionActionEntity> list = new ArrayList<>();

        list.add(solutionActionEntity);

        List<Action> resultList = solutionActionMapper.mapList(list);

        assertEquals(1, resultList.size());

        Action result = resultList.get(0);

        assertEquals(COMMENT, result.getComment());
        assertEquals(ACTION_TYPE_ID, result.getStatus().getCode());
        assertEquals(SOLUTION_LAST_ACTION_TS, result.getTime());

    }

}