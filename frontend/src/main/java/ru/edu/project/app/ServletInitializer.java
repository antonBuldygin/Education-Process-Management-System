package ru.edu.project.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * App configuration.
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(
            final SpringApplicationBuilder application
    ) {
        return application.sources(EPMSFrontendApplication.class);
    }

}
