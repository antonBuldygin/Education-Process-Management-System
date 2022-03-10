package ru.edu.project.backend.app;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ServletInitializerTest {

    private ServletInitializer servletInitializer = new ServletInitializer();

    @Test
    public void configure() {
        openMocks(this);

        SpringApplicationBuilder application = mock(SpringApplicationBuilder.class);

        servletInitializer.configure(application);

        verify(application).sources(EPMSBackendApplication.class);

    }

}