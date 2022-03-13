package ru.edu.project.app;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.edu.project.frontend.controller.users.AdminController;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ServletInitializerTest {

    @InjectMocks
    private ServletInitializer servletInitializer;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void configure() {
        SpringApplicationBuilder application = mock(SpringApplicationBuilder.class);

        when(application.sources(EPMSFrontendApplication.class)).thenReturn(application);

        servletInitializer.configure(application);

        verify(application).sources(EPMSFrontendApplication.class);
    }
}