package ru.edu.project.backend.api.teachers;

import ru.edu.project.backend.api.common.AcceptorArgument;

import java.util.List;

public interface TeacherService {

    /**
     * Выбор всех преподавателей.
     *
     * @return список
     */
    List<Teacher> getAvailable();

    /**
     * Выбор преподавателя по коду.
     *
     * @param ids
     * @return список
     */
    @AcceptorArgument
    List<Teacher> getByIds(List<Long> ids);

}
