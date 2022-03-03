package ru.edu.project.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ru.edu.project"})
public class EPMSFrontendApplication {

    /**
     * Entry point.
     *
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(EPMSFrontendApplication.class, args);
    }

}
