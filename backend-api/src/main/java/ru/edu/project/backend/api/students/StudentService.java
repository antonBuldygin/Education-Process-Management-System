package ru.edu.project.backend.api.students;


public interface StudentService {

    StudentInfo createStudent(StudentForm studentForm);

    StudentInfo editStudent(StudentForm studentForm);

    /**
     * Get task by id.
     *
     * @param id
     * @return TaskInfo
     */
    StudentInfo getById(long id);

    int deleteById(long id);
}
