package ru.edu.project.backend.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ru.edu.project.backend"})
public class EPMSBackendApplication {

    /**
     * Entry point.
     *
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(EPMSBackendApplication.class, args);
    }

}
