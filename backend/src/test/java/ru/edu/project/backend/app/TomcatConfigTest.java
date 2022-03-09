package ru.edu.project.backend.app;

import org.junit.Test;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.jndi.JndiObjectFactoryBean;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TomcatConfigTest {

    TomcatConfig tomcatConfig = new TomcatConfig();

    @Test
    public void jndiDataSource() {
        JndiObjectFactoryBean bean = mock(JndiObjectFactoryBean.class);

        verify(bean).setJndiName("java:comp/env/jdbc/backendDbLink");
    }
}