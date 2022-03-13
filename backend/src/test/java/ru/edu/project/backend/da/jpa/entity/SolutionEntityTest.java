package ru.edu.project.backend.da.jpa.entity;

import org.junit.Test;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.model.SolutionStatus;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class SolutionEntityTest {

    public static final Timestamp CREATION_TIME_TS = new Timestamp(1L);
    public static final Timestamp CHECKED_TIME_TS = new Timestamp(2L);
    public static final Timestamp LAST_ACTION_TIME_TS = new Timestamp(3L);
    public static final Long SOLUTION_ID = 111L;
    public static final Long TASK_ID = 222L;
    public static final Long STUDENT_ID = 333L;
    public static final String TEXT = "Text";
    public static final Long STATUS = 1L;
    public static final Integer SCORE = 50;

    @Test
    public void entityTest() {
        SolutionEntity entity = new SolutionEntity();
        entity.setId(SOLUTION_ID);
        entity.setCheckedTime(CHECKED_TIME_TS);
        entity.setCreationTime(CREATION_TIME_TS);
        entity.setLastActionTime(LAST_ACTION_TIME_TS);
        entity.setScore(new Score(SCORE));
        entity.setStatus(SolutionStatus.byCode(STATUS));
        entity.setText(TEXT);
        entity.setTaskId(TASK_ID);
        entity.setStudentId(STUDENT_ID);

        assertEquals(SOLUTION_ID, entity.getId());
        assertEquals(CHECKED_TIME_TS, entity.getCheckedTime());
        assertEquals(CREATION_TIME_TS, entity.getCreationTime());
        assertEquals(LAST_ACTION_TIME_TS, entity.getLastActionTime());
        assertEquals(SCORE, entity.getScore().getValue());
        assertEquals(STATUS, entity.getStatus().getCode());
        assertEquals(TEXT, entity.getText());
        assertEquals(TASK_ID, entity.getTaskId());
        assertEquals(STUDENT_ID, entity.getStudentId());
        assertNull(entity.getActionHistory());
    }

}