package ru.edu.project.backend.da;

import ru.edu.project.backend.api.teachers.Teacher;

import java.util.List;

public interface TeachersDALayer {

    /**
     * Связывание запроса и услуги.
     *
     * @param requestId
     * @param jobId
     */
    void linkRequest(long requestId, long jobId);

    /**
     * Получение услуги по id.
     *
     * @param id
     * @return job
     */
    Teacher getById(Long id);

    /**
     * Получение списка услуг по ids.
     *
     * @param ids
     * @return list job
     */
    List<Teacher> getByIds(List<Long> ids);

    /**
     * Получение списка доступных услуг.
     *
     * @return list job
     */
    List<Teacher> getAvailable();

    /**
     * Получение списка услуг заявки по requestId.
     *
     * @param requestId
     * @return list job
     */
    List<Teacher> getLinksByRequestId(long requestId);


}
