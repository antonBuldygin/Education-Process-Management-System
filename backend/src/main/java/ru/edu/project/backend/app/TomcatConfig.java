package ru.edu.project.backend.app;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class TomcatConfig {

    /**
     * Jndi configuration for build-in Tomcat.
     *
     * @return tomcatServletWebServerFactory
     */
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory() {

            /**
             * Jndi activation.
             *
             * @param tomcat
             * @return tomcat
             */
            @Override
            protected TomcatWebServer getTomcatWebServer(final Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            /**
             * Datasource creating.
             *
             * @param context
             */
            @Override
            protected void postProcessContext(final Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/backendDbLink");
                resource.setType(DataSource.class.getName());

                resource.setProperty("factory", "com.zaxxer.hikari.HikariJNDIFactory");
                resource.setProperty("driverClassName", "org.h2.Driver");
                resource.setProperty("jdbcUrl", "jdbc:h2:./backend_db");
                resource.setProperty("username", "sa");
                resource.setProperty("password", "");

                context.getNamingResources()
                        .addResource(resource);
            }
        };
    }

    /**
     * Getting jndi datasource.
     *
     * @return datasource
     * @throws IllegalArgumentException
     * @throws NamingException
     */
    @Bean(destroyMethod = "")
    public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/backendDbLink");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }

}
