package ru.edu.project.backend.api.teachers;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class Teacher implements TeacherAbstract {

    /**
     * Код .
     */
    private Long id;

    /**
     * Имя преподавателя.
     */
    private String teacherName;

    /**
     * Предмет.
     */
    private String course;
}
