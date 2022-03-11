package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.api.students.StudentForm;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.backend.service.StudentServiceLayer;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController implements StudentService {

    /**
     * Delegate for task service layer.
     */
    @Autowired
    private StudentServiceLayer delegate;


    /**
     * Create new student.
     *
     * @param studentForm
     * @return StudentInfo
     */
    @Override
    @PostMapping("/createStudent")
    public StudentInfo createStudent(@RequestBody final StudentForm studentForm) {
        return delegate.createStudent(studentForm);
    }

    /**
     * Edit student.
     *
     * @param studentForm
     * @return StudentInfo
     */
    @Override
    @PostMapping("/editStudent")
    public StudentInfo editStudent(@RequestBody final StudentForm studentForm) {
        return delegate.editStudent(studentForm);
    }


    /**
     * Get student by id.
     *
     * @param id
     * @return StudentInfo
     */
    @Override
    @GetMapping("/getById/{id}")
    public StudentInfo getById(@PathVariable("id") final long id) {
        return delegate.getById(id);
    }

    /**
     * Delete task by id.
     *
     * @param id
     * @return StudentInfo
     */
    @Override
    @GetMapping("/deleteById/{id}")
    public int deleteById(@PathVariable("id") final long id) {
        return delegate.deleteById(id);
    }

    /**
     * Getting all students.
     *
     * @return list of tasks
     */
    @Override
    @GetMapping("/getAllStudents")
    public List<StudentInfo> getAllStudents() {
        return delegate.getAllStudents();
    }
}

