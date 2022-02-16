package ru.edu.project.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.edu.project.backend.api.teachers.TeacherService;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.stub.jobs.InMemoryStubTeacherService;
import ru.edu.project.backend.stub.requests.InMemoryStubGroupsService;

@Configuration
@Profile("STUBS")
public class StubsConfig {

    /**
     * Заглушка сервиса.
     *
     * @return bean
     */
    @Bean
    public TeacherService jobServiceBean() {
        return new InMemoryStubTeacherService();
    }

    /**
     * Заглушка сервиса.
     * @param teacherService
     * @return bean
     */
    @Bean
    public GroupsService requestServiceBean(final TeacherService teacherService) {
        return new InMemoryStubGroupsService(teacherService);
    }

}
