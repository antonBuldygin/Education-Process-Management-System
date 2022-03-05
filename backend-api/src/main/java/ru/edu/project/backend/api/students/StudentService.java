package ru.edu.project.backend.api.students;

public interface StudentService {

    /**
     * Get student by id.
     *
     * @param id
     * @return StudentInfo
     */
    StudentInfo getById(long id);
}
