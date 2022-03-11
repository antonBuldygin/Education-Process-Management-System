package ru.edu.project.backend.da.jpa.entity;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class SolutionActionEntityTest {

    public static final String COMMENT = "Comment";
    public static final Timestamp ACTION_TIME_TS = new Timestamp(1L);
    public static final Long ACTION_TYPE_ID = 222L;
    public static final Long SOLUTION_ID = 333L;

    @Test
    public void entityTest() {
        SolutionActionEntity.SolutionActionLinkId pk = SolutionActionEntity.pk(SOLUTION_ID, ACTION_TYPE_ID);

        SolutionActionEntity entity = new SolutionActionEntity();
        entity.setComment(COMMENT);
        entity.setActionTime(ACTION_TIME_TS);
        entity.setPk(pk);

        assertEquals(SOLUTION_ID, entity.getPk().getSolutionId());
        assertEquals(ACTION_TYPE_ID, entity.getPk().getActionTypeId());
        assertEquals(COMMENT, entity.getComment());
        assertEquals(ACTION_TIME_TS, entity.getActionTime());

    }

}