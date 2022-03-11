package ru.edu.project.backend.da.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.da.TeachersDALayer;

import java.util.List;

@Service
@Profile("SPRING_DATA")
public class TeachersJpaDa implements TeachersDALayer {
    /**
     * Связывание запроса и услуги.
     *
     * @param requestId
     * @param jobId
     */
    @Override
    public void linkRequest(final long requestId, final long jobId) {

    }

    /**
     * Получение услуги по id.
     *
     * @param id
     * @return job
     */
    @Override
    public Teacher getById(final Long id) {
        return null;
    }

    /**
     * Получение списка услуг по ids.
     *
     * @param ids
     * @return list job
     */
    @Override
    public List<Teacher> getByIds(final List<Long> ids) {
        return null;
    }

    /**
     * Получение списка доступных услуг.
     *
     * @return list job
     */
    @Override
    public List<Teacher> getAvailable() {
        return null;
    }

    /**
     * Получение списка услуг заявки по requestId.
     *
     * @param requestId
     * @return list job
     */
    @Override
    public List<Teacher> getLinksByRequestId(final long requestId) {
        return null;
    }
}
