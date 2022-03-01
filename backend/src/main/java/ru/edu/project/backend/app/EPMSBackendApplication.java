package ru.edu.project.backend.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

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
