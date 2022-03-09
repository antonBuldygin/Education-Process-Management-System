package ru.edu.project.backend.api.students;


import ru.edu.project.backend.api.common.AcceptorArgument;

import java.util.List;

public interface StudentService {

    /**
     * createStudent.
     *
     * @param studentForm
     * @return StudentInfo
     */
    @AcceptorArgument
    StudentInfo createStudent(StudentForm studentForm);

    /**
     * editStudent.
     *
     * @param studentForm
     * @return StudentInfo
     */
    @AcceptorArgument
    StudentInfo editStudent(StudentForm studentForm);

    /**
     * Get task by id.
     *
     * @param id
     * @return TaskInfo
     */
    StudentInfo getById(long id);

    /**
     * deleteById.
     *
     * @param id
     * @return int
     */
    int deleteById(long id);

    /**
     * Getting all students.
     *
     * @return list of tasks
     */
    List<StudentInfo> getAllStudents();
}
