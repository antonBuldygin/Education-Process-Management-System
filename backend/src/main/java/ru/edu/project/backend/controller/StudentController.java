package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.backend.service.StudentServiceLayer;



@RestController
@RequestMapping("/student")
public class StudentController implements StudentService {

    /**
     * Delegate for student service layer.
     */
    @Autowired
    private StudentServiceLayer delegate;


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

}
