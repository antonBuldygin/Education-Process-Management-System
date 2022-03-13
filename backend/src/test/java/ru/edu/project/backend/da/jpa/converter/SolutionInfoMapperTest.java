package ru.edu.project.backend.da.jpa.converter;

import org.junit.Before;
import org.junit.Test;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.da.jpa.entity.SolutionEntity;
import ru.edu.project.backend.model.SolutionStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SolutionInfoMapperTest {

    public static final Long SOLUTION_ID = 111L;
    public static final Long TASK_ID = 222L;
    public static final Long STUDENT_ID = 333L;
    public static final String TEXT = "Text";
    public static final Integer SCORE = 50;
    public static final Timestamp CREATION_TS = new Timestamp(1L);
    public static final Timestamp LAST_ACTION_TS = new Timestamp(1L);
    public static final Timestamp CHECKED_TS = new Timestamp(1L);


    SolutionInfoMapper solutionInfoMapper;

    @Before
    public void setUp() throws Exception {
        solutionInfoMapper = new SolutionInfoMapperImpl();
    }

    @Test
    public void infoToEntity() {
        SolutionInfo solutionInfo = SolutionInfo.builder()
                .id(SOLUTION_ID)
                .taskId(TASK_ID)
                .studentId(STUDENT_ID)
                .text(TEXT)
                .status(SolutionStatus.TASK_IN_WORK)
                .score(new Score(SCORE))
                .creationTime(CREATION_TS)
                .lastActionTime(LAST_ACTION_TS)
                .checkedTime(CHECKED_TS)
                .build();

        SolutionEntity result = solutionInfoMapper.map(solutionInfo);

        assertEquals(SOLUTION_ID, result.getId());
        assertEquals(TASK_ID, result.getTaskId());
        assertEquals(STUDENT_ID, result.getStudentId());
        assertEquals(TEXT, result.getText());
        assertEquals(SolutionStatus.TASK_IN_WORK.getCode(), result.getStatus().getCode());
        assertEquals(SCORE, result.getScore().getValue());
        assertEquals(CREATION_TS, result.getCreationTime());
        assertEquals(LAST_ACTION_TS, result.getLastActionTime());
        assertEquals(CHECKED_TS, result.getCheckedTime());
    }

    @Test
    public void infoToEntityNull() {
        SolutionInfo solutionInfo = null;

        SolutionEntity result = solutionInfoMapper.map(solutionInfo);

        assertNull(result);
    }

    @Test
    public void entityToInfo() {
        SolutionEntity solutionEntity = new SolutionEntity();

        solutionEntity.setId(SOLUTION_ID);
        solutionEntity.setStudentId(STUDENT_ID);
        solutionEntity.setTaskId(TASK_ID);
        solutionEntity.setText(TEXT);
        solutionEntity.setScore(new Score(SCORE));
        solutionEntity.setStatus(SolutionStatus.TASK_IN_WORK);
        solutionEntity.setCreationTime(CREATION_TS);
        solutionEntity.setLastActionTime(LAST_ACTION_TS);
        solutionEntity.setCheckedTime(CHECKED_TS);

        SolutionInfo result = solutionInfoMapper.map(solutionEntity);

        assertEquals(SOLUTION_ID, result.getId());
        assertEquals(STUDENT_ID, result.getStudentId());
        assertEquals(TASK_ID, result.getTaskId());
        assertEquals(TEXT, result.getText());
        assertEquals(SCORE, result.getScore().getValue());
        assertEquals(SolutionStatus.TASK_IN_WORK.getCode(), result.getStatus().getCode());
        assertEquals(CREATION_TS, result.getCreationTime());
        assertEquals(LAST_ACTION_TS, result.getLastActionTime());
        assertEquals(CHECKED_TS, result.getCheckedTime());

    }

    @Test
    public void entityToInfoNull() {
        SolutionEntity solutionEntity = null;

        SolutionInfo result = solutionInfoMapper.map(solutionEntity);

        assertNull(result);
    }

    @Test
    public void listEntityToInfo() {
        SolutionEntity solutionEntity = new SolutionEntity();

        solutionEntity.setId(SOLUTION_ID);
        solutionEntity.setStudentId(STUDENT_ID);
        solutionEntity.setTaskId(TASK_ID);
        solutionEntity.setText(TEXT);
        solutionEntity.setScore(new Score(SCORE));
        solutionEntity.setStatus(SolutionStatus.TASK_IN_WORK);
        solutionEntity.setCreationTime(CREATION_TS);
        solutionEntity.setLastActionTime(LAST_ACTION_TS);
        solutionEntity.setCheckedTime(CHECKED_TS);

        List<SolutionEntity> solutionEntityList = new ArrayList<>();

        solutionEntityList.add(solutionEntity);

        List<SolutionInfo> resultList = solutionInfoMapper.mapList(solutionEntityList);

        assertEquals(1, resultList.size());

        SolutionInfo result = resultList.get(0);

        assertEquals(SOLUTION_ID, result.getId());
        assertEquals(STUDENT_ID, result.getStudentId());
        assertEquals(TASK_ID, result.getTaskId());
        assertEquals(TEXT, result.getText());
        assertEquals(SCORE, result.getScore().getValue());
        assertEquals(SolutionStatus.TASK_IN_WORK.getCode(), result.getStatus().getCode());
        assertEquals(CREATION_TS, result.getCreationTime());
        assertEquals(LAST_ACTION_TS, result.getLastActionTime());
        assertEquals(CHECKED_TS, result.getCheckedTime());

    }

    @Test
    public void listEntityToInfoNull() {
        List<SolutionEntity> entityList = null;

        List<SolutionInfo> result = solutionInfoMapper.mapList(entityList);

        assertNull(result);
    }

    @Test
    public void iterableEntityToInfo() {
        SolutionEntity solutionEntity = new SolutionEntity();

        solutionEntity.setId(SOLUTION_ID);
        solutionEntity.setStudentId(STUDENT_ID);
        solutionEntity.setTaskId(TASK_ID);
        solutionEntity.setText(TEXT);
        solutionEntity.setScore(new Score(SCORE));
        solutionEntity.setStatus(SolutionStatus.TASK_IN_WORK);
        solutionEntity.setCreationTime(CREATION_TS);
        solutionEntity.setLastActionTime(LAST_ACTION_TS);
        solutionEntity.setCheckedTime(CHECKED_TS);

        List<SolutionEntity> solutionEntityList = new ArrayList<>();

        solutionEntityList.add(solutionEntity);

        List<SolutionInfo> resultList = solutionInfoMapper.map(solutionEntityList);

        assertEquals(1, resultList.size());

        SolutionInfo result = resultList.get(0);

        assertEquals(SOLUTION_ID, result.getId());
        assertEquals(STUDENT_ID, result.getStudentId());
        assertEquals(TASK_ID, result.getTaskId());
        assertEquals(TEXT, result.getText());
        assertEquals(SCORE, result.getScore().getValue());
        assertEquals(SolutionStatus.TASK_IN_WORK.getCode(), result.getStatus().getCode());
        assertEquals(CREATION_TS, result.getCreationTime());
        assertEquals(LAST_ACTION_TS, result.getLastActionTime());
        assertEquals(CHECKED_TS, result.getCheckedTime());

    }

    @Test
    public void iterableEntityToInfoNull() {
        List<SolutionEntity> entityList = null;

        List<SolutionInfo> result = solutionInfoMapper.map(entityList);

        assertNull(result);
    }
}