package ru.edu.project.frontend.controller.forms.solutions;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionCreateFormTest {

    public static final Long TASK_ID = 1L;

    @Test
    public void solutionCreateFormTest() {
        SolutionCreateForm solutionCreateForm = new SolutionCreateForm();

        solutionCreateForm.setTask(TASK_ID);

        Long task = solutionCreateForm.getTask();

        assertEquals(TASK_ID, task);

    }

}