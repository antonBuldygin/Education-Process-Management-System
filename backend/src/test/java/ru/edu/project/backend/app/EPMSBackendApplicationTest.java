package ru.edu.project.backend.app;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class EPMSBackendApplicationTest {

    @InjectMocks
    EPMSBackendApplication app;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void jdbcInsertBean() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        SimpleJdbcInsert simpleJdbcInsert = app.jdbcInsertBean(jdbcTemplate);

        assertEquals(jdbcTemplate, simpleJdbcInsert.getJdbcTemplate());
   }

    @Test
    public void namedParameterJdbcTemplate() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = app.namedParameterJdbcTemplate(jdbcTemplate);

        assertEquals(jdbcTemplate, namedParameterJdbcTemplate.getJdbcTemplate());
    }
}