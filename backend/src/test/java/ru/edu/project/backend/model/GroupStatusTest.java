package ru.edu.project.backend.model;

import org.junit.Test;
import ru.edu.project.backend.api.common.Status;

import static org.junit.Assert.*;

public class GroupStatusTest {

    @Test
    public void byCode() {
        Long statusCode = 2L;

        Status result = GroupStatus.byCode(statusCode);

        assertEquals(statusCode, result.getCode());

        assertEquals("В работе", result.getMessage());

    }

}