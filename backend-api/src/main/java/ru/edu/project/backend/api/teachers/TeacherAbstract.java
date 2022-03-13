package ru.edu.project.backend.api.teachers;

public interface TeacherAbstract {
    /**
     * id.
     *
     * @return long
     */
    Long getId();

    /**
     * title.
     *
     * @return string
     */
    String getTeacherName();

    /**
     * desc.
     *
     * @return string
     */
    String getCourse();

}
