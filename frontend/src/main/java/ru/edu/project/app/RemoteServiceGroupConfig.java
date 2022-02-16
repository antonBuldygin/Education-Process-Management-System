package ru.edu.project.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.edu.project.backend.RestServiceInvocationHandler;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.teachers.TeacherService;

import java.lang.reflect.Proxy;

@Configuration
@Profile("REST")
@SuppressWarnings("unchecked")
public class RemoteServiceGroupConfig {

    /**
     * Создаем rest-прокси для GroupsService.
     *
     * @param handler
     * @return rest-proxy
     */
    @Bean
    public GroupsService requestServiceRest(final RestServiceInvocationHandler handler) {
        handler.setServiceUrl("/group");
        return getProxy(handler, GroupsService.class);
    }

    /**
     * Создаем rest-прокси для GroupsService.
     *
     * @param handler
     * @return rest-proxy
     */
    @Bean
    public TeacherService jobService(final RestServiceInvocationHandler handler) {
        handler.setServiceUrl("/job");
        return getProxy(handler, TeacherService.class);
    }


    private <T> T getProxy(final RestServiceInvocationHandler handler, final Class<T>... tClass) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), tClass, handler);
    }

}
