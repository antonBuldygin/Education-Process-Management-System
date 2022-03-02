package ru.edu.project.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@Controller
public class StudentController {


    /**
     * Displaying student's solutions.
     *
     * @param model
     * @return view
     */
    @GetMapping("/")
    public String index(final Model model) {
        long studentId = 1; //todo в дальнейшим заменим на id пользователя
        long groupId = 1; //todo заменить на получение номера группы

        model.addAttribute(
                "groupId",
                groupId
        );

        return "student/index";
    }

}
