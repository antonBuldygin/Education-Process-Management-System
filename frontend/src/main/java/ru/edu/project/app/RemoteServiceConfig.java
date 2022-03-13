package ru.edu.project.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.edu.project.backend.RestServiceInvocationHandler;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.backend.api.tasks.TaskService;
import ru.edu.project.backend.api.user.UserService;

import java.lang.reflect.Proxy;

@Configuration
@Profile("REST")
@SuppressWarnings("unchecked")
public class RemoteServiceConfig {

    /**
     * Rest-proxy for SolutionService.
     *
     * @param handler
     * @return rest-proxy
     */
    @Bean
    public SolutionService solutionServiceRest(final RestServiceInvocationHandler handler) {
        handler.setServiceUrl("/solution");
        return getProxy(handler, SolutionService.class);
    }

    /**
     * Rest-proxy for TaskService.
     *
     * @param handler
     * @return rest-proxy
     */
    @Bean
    public TaskService taskService(final RestServiceInvocationHandler handler) {
        handler.setServiceUrl("/task");
        return getProxy(handler, TaskService.class);
    }

    /**
     * Rest-proxy for UserService.
     *
     * @param handler
     * @return rest-proxy
     */
    @Bean
    public UserService userService(final RestServiceInvocationHandler handler) {
        handler.setServiceUrl("/user");
        return getProxy(handler, UserService.class);
    }

    /**
     * Rest-proxy for StudentService.
     *
     * @param handler
     * @return rest-proxy
     */
    @Bean
    public StudentService studentService(final RestServiceInvocationHandler handler) {
        handler.setServiceUrl("/student");
        return getProxy(handler, StudentService.class);
    }


    private <T> T getProxy(final RestServiceInvocationHandler handler, final Class<T>... tClass) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), tClass, handler);
    }

}
