package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.teachers.Teacher;

import ru.edu.project.backend.api.teachers.TeacherService;
import ru.edu.project.backend.da.TeachersDALayer;

import java.util.List;

@Service
@Profile("!STUB")
@Qualifier("TeacherServiceLayer")
public class TeacherServiceLayer implements TeacherService {

    /**
     * Зависимость для слоя доступа к данным услуг.
     */
    @Autowired
    private TeachersDALayer daLayer;

    /**
     * Получение доступных услуг.
     *
     * @return список
     */
    @Override
    public List<Teacher> getAvailable() {
        return daLayer.getAvailable();
    }

    /**
     * Получение услуг по коду.
     *
     * @param ids
     * @return список
     */
    @Override
    public List<Teacher> getByIds(final List<Long> ids) {
        return daLayer.getByIds(ids);
    }

    /**
     * Получение услуг связанных с заявкой по requestId.
     *
     * @param requestId
     * @return list job
     */
    public List<Teacher> getByLink(final long requestId) {
        return daLayer.getLinksByRequestId(requestId);
    }

    /**
     * Связывание заявки и услуги.
     *
     * @param requestId
     * @param ids
     */
    public void link(final long requestId, final List<Long> ids) {
        ids.forEach(id -> daLayer.linkRequest(requestId, id));
    }
}
