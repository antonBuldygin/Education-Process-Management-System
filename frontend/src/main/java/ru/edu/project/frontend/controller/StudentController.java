//package ru.edu.project.frontend.controller;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//
//import org.springframework.validation.BindingResult;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//import ru.edu.project.backend.api.solutions.SolutionInfo;
//import ru.edu.project.backend.api.solutions.SolutionService;
//import ru.edu.project.backend.api.students.StudentForm;
//import ru.edu.project.backend.api.students.StudentInfo;
//import ru.edu.project.backend.api.students.StudentService;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//@RequestMapping("/student")
//@Controller
//public class StudentController {
//
//    /**
//     * Model's attribute for storing errors.
//     */
//    public static final String FORM_ERROR_ATTR = "errorsList";
//
//    /**
//     * Model's attribute for storing solutions.
//     */
//    public static final String STUDENT_ATTR = "students";
//
//
//    /**
//     * Student service.
//     */
//    @Autowired
//    private StudentService studentService;
//
//
//    /**
//     * Solution service.
//     */
//    @Autowired
//    private SolutionService solutionService;
//
//    /**
//     * Displaying student's info.
//     *
//     * @param model
//     * @return view
//     */
//    @GetMapping("/")
//    public String index(final Model model) {
//
//        model.addAttribute(
//                STUDENT_ATTR,
//                studentService.getAllStudents()
//        );
//
//        return "student/index";
//    }
//
//    /**
//     * View student by id.
//     *
//     * @param studentId
//     * @return modelAndView
//     */
//    @GetMapping("/view/{id}")
//    public ModelAndView view(@PathVariable("id") final Long studentId) {
//
//        ModelAndView model = new ModelAndView("student/view");
//
//        StudentInfo studentInfo = studentService.getById(studentId);
//
//        model.addObject(
//                "record",
//                studentInfo
//        );
//
//        return model;
//    }
//
//    /**
//     * View deleted student by id.
//     *
//     * @param model
//     * @param studentId
//     * @return modelAndView
//     */
//    @GetMapping("/delete/{id}")
//    public String delete(final Model model,
//                         @PathVariable("id") final Long studentId
//                        ) {
//
//        List<SolutionInfo> solutionsByTask = solutionService.getSolutionsByStudent(studentId);
//
//        if (solutionsByTask != null && solutionsByTask.size() > 0) {
//            return "student/deleteError";
//        }
//
//        int deletedInfo = studentService.deleteById(studentId);
//
//        return "redirect:/student/";
//    }
//
//    /**
//     * View for creating new student.
//     *
//     * @param model
//     * @return view
//     */
//    @GetMapping("/create")
//    public String createForm(final Model model) {
//        return "student/create";
//    }
//
//
//    /**
//     * Processing of creation form.
//     *
//     * @param form
//     * @param bindingResult
//     * @param model
//     * @return redirect url
//     */
//    @PostMapping("/create")
//    public String createFormProcessing(
//            @Valid
//            @ModelAttribute final CreateForm form,
//            final BindingResult bindingResult,
//            final Model model
//    ) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute(
//                    FORM_ERROR_ATTR,
//                    bindingResult.getAllErrors()
//            );
//
//            return createForm(model);
//        }
//
//        StudentInfo studentInfo = studentService.createStudent(StudentForm.builder()
//                .groupId(form.getGroupId())
//                .firstName(form.getFirstName())
//                .lastName(form.getLastName())
//                .birthday(form.getBirthday())
//                .email(form.getEmail())
//                .phone(form.getPhone())
//                .build());
//
//        return "redirect:/student/?created=" + studentInfo.getId();
//    }
//
//    @Getter
//    @Setter
//    public static class CreateForm {
//
//        /**
//         * Format for date parsing.
//         */
//        private static final DateFormat FORMAT;
//
//        static {
//            FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//        }
//
//        /**
//         * GroupId.
//         */
//        private Long groupId;
//
//        /**
//         * firstName.
//         */
//        @NotNull
//        private String firstName;
//
//        /**
//         * lastName.
//         */
//        @NotNull
//        private String lastName;
//
//        /**
//         * email.
//         */
//        private String email;
//
//        /**
//         * birthday.
//         */
//        @NotEmpty
//        private String birthday;
//
//        /**
//         * phone.
//         */
//        @NotEmpty
//        private String phone;
//
//        /**
//         * Getting calendar object for birthday.
//         *
//         * @return Timestamp
//         */
//        @SneakyThrows
//        public Timestamp getBirthday() {
//            Date parsed = FORMAT.parse(birthday);
//            return new Timestamp(parsed.getTime());
//        }
//
//    }
//
//    /**
//     * View for editing student.
//     *
//     * @param studentId
//     * @return view
//     */
//    @GetMapping("/edit/{studentId}")
//    public ModelAndView editForm(@PathVariable("studentId") final Long studentId) {
//
//        ModelAndView model = new ModelAndView("student/edit");
//
//        StudentInfo studentInfo = studentService.getById(studentId);
//
//        model.addObject(
//                "record",
//                studentInfo
//        );
//
//        return model;
//    }
//
//
//    /**
//     * Processing of edit form.
//     *
//     * @param studentId
//     * @param form
//     * @param bindingResult
//     * @param model
//     * @return redirect url
//     */
//    @PostMapping("/edit/{studentId}")
//    public String editFormProcessing(
//            @Valid
//            @ModelAttribute final CreateForm form,
//            @PathVariable("studentId") final Long studentId,
//            final BindingResult bindingResult,
//            final Model model
//    ) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute(
//                    FORM_ERROR_ATTR,
//                    bindingResult.getAllErrors()
//            );
//
//            return createForm(model);
//        }
//
//        StudentInfo studentInfo = studentService.editStudent(StudentForm.builder()
////                .id(studentId)
//                .groupId(form.getGroupId())
//                .firstName(form.getFirstName())
//                .lastName(form.getLastName())
//                .birthday(form.getBirthday())
//                .email(form.getEmail())
//                .phone(form.getPhone())
//                .build());
//
//        return "redirect:/student/view/" + studentInfo.getId();
//    }
//}
