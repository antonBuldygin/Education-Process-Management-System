package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.api.teachers.TeacherService;
import ru.edu.project.backend.service.TeacherServiceLayer;

import java.util.List;

@RestController
@RequestMapping("/job")
public class TeacherController implements TeacherService {

    /**
     * Делегат для передачи вызова.
     */
    @Autowired
    private TeacherServiceLayer delegate;

    /**
     * Получение доступных услуг.
     *
     * @return список
     */
    @Override
    @GetMapping("/getAvailable")
    public List<Teacher> getAvailable() {
        return delegate.getAvailable();
    }

    /**
     * Получение услуг по коду.
     *
     * @param ids
     * @return список
     */
    @Override
    @PostMapping("/getByIds")
    public List<Teacher> getByIds(final @RequestBody List<Long> ids) {
        return delegate.getByIds(ids);
    }
}
