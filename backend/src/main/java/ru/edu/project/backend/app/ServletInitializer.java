package ru.edu.project.backend.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Application configuration.
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(
            final SpringApplicationBuilder application
    ) {
        return application.sources(EPMSBackendApplication.class);
    }

}
