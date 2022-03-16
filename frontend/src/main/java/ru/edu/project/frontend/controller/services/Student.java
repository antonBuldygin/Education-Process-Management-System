package ru.edu.project.frontend.controller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.students.StudentForm;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.frontend.controller.forms.students.StudentCreateForm;


import java.util.List;

@Component
public class Student {

    /**
     * Model's attribute for storing errors.
     */
    public static final String FORM_ERROR_ATTR = "errorsList";

    /**
     * Model's attribute for storing solutions.
     */
    public static final String STUDENT_ATTR = "students";

    /**
     * Model's attribute for storing role.
     */
    public static final String ROLE = "role";

    /**
     * Student service.
     */
    @Autowired
    private StudentService studentService;

    /**
     * Group service.
     */
    @Autowired
    private GroupsService groupsService;

    /**
     * Solution service.
     */
    @Autowired
    private SolutionService solutionService;

    /**
     * Displaying all student's info.
     *
     * @param model
     * @param role
     * @return view
     */
    public String index(final Model model, final String role) {

        model.addAttribute(
                ROLE,
                role
        );

        model.addAttribute(
                STUDENT_ATTR,
                studentService.getAllStudents()
        );

        return "person/index";
    }

    /**
     * View student by id.
     *
     * @param studentId
     * @param role
     * @return modelAndView
     */
    public ModelAndView view(final Long studentId, final String role) {

        ModelAndView model = new ModelAndView("person/view");

        StudentInfo studentInfo = studentService.getById(studentId);

        model.addObject(
                "record",
                studentInfo
        );

        model.addObject(
                ROLE,
                role
        );

        return model;
    }

    /**
     * View deleting student by id.
     *
     * @param model
     * @param studentId
     * @param role
     * @return modelAndView
     */
    public String delete(final Model model,
                         final Long studentId,
                         final String role
    ) {

        List<SolutionInfo> solutionsByTask = solutionService.getSolutionsByStudent(studentId);

        if (solutionsByTask != null && solutionsByTask.size() > 0) {

            model.addAttribute(
                    ROLE,
                    role
            );

            return "person/deleteError";
        }

        model.addAttribute(
                ROLE,
                role
        );

        int deletedInfo = studentService.deleteById(studentId);

        return "redirect:/" + role + "/person/all";
    }

    /**
     * View for creating new student.
     *
     * @param model
     * @param role
     * @return view
     */
    public String createForm(final Model model, final String role) {

        model.addAttribute(
                ROLE,
                role
        );

        model.addAttribute(
                "groups",
                groupsService.getAllGroupsInfo()
        );

        return "person/create";
    }


    /**
     * Processing of creation form.
     *
     * @param form
     * @param bindingResult
     * @param model
     * @param role
     * @return redirect url
     */
    public String createFormProcessing(
            final StudentCreateForm form,
            final BindingResult bindingResult,
            final Model model,
            final String role
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return createForm(model, role);
        }

        model.addAttribute(
                ROLE,
                role
        );

        StudentInfo studentInfo = studentService.createStudent(StudentForm.builder()
                .groupId(form.getGroupId())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthday(form.getBirthday())
                .email(form.getEmail())
                .phone(form.getPhone())
                .build());

        return "redirect:/" + role + "/person/all?created=" + studentInfo.getId();
    }


    /**
     * View for editing student.
     *
     * @param studentId
     * @param role
     * @return view
     */
    public ModelAndView editForm(final Long studentId, final String role) {

        ModelAndView model = new ModelAndView("person/edit");

        StudentInfo studentInfo = studentService.getById(studentId);

        model.addObject(
                ROLE,
                role
        );

        model.addObject(
                "groups",
                groupsService.getAllGroupsInfo()
        );

        model.addObject(
                "record",
                studentInfo
        );

        return model;
    }


    /**
     * Processing of edit form.
     *
     * @param studentId
     * @param form
     * @param bindingResult
     * @param model
     * @param role
     * @return redirect url
     */
    public String editFormProcessing(
            final StudentCreateForm form,
            final Long studentId,
            final BindingResult bindingResult,
            final Model model,
            final String role
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return createForm(model, role);
        }

        model.addAttribute(
                ROLE,
                role
        );

        StudentInfo studentInfo = studentService.editStudent(StudentForm.builder()
                .id(studentId)
                .groupId(form.getGroupId())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .birthday(form.getBirthday())
                .email(form.getEmail())
                .phone(form.getPhone())
                .build());

        return "redirect:/" + role + "/person/view/" + studentInfo.getId();
    }
}
