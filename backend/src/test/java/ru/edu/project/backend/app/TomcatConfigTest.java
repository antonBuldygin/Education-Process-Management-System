package ru.edu.project.backend.app;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class TomcatConfigTest {

    TomcatConfig tomcatConfig = new TomcatConfig();

    @Test
    @SneakyThrows
    public void jndiDataSource() {
        JndiObjectFactoryBean bean = mock(JndiObjectFactoryBean.class);

        DataSource dataSource = tomcatConfig.jndiDataSource();

        assertNotNull(dataSource);
    }
}