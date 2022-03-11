package ru.edu.project.backend.app;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

import static org.mockito.MockitoAnnotations.openMocks;

public class IndexControllerTest {
    @InjectMocks
    private IndexController indexController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void index() {

        String result = indexController.index();

        assertEquals("index", result);
    }
}