package ru.edu.project.backend.da;

import ru.edu.project.backend.api.students.StudentInfo;

import java.util.List;


public interface StudentDALayer {


    /**
     * Saving student.
     *
     * @param draft
     * @return StudentInfo
     */
    StudentInfo save(StudentInfo draft);

    /**
     * Get student by id.
     *
     * @param id
     * @return список
     */
    StudentInfo getById(long id);

    /**
     * Delete student by id.
     *
     * @param id
     * @return int
     */
    int deleteById(long id);

    /**
     * Getting all students.
     *
     * @return list of students
     */
    List<StudentInfo> getAllStudents();
}

