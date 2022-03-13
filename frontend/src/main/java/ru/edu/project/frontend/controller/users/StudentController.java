package ru.edu.project.frontend.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.authorization.UserDetailsId;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.frontend.controller.forms.solutions.SolutionCreateForm;
import ru.edu.project.frontend.controller.forms.solutions.SolutionUploadForm;
import ru.edu.project.frontend.controller.services.Solution;
import ru.edu.project.frontend.controller.services.Task;
import ru.edu.project.frontend.controller.services.Student;

import javax.validation.Valid;

@RequestMapping("/student")
@Controller
public class StudentController {

    /**
     * Frontend solution service.
     */
    @Autowired
    private Solution solution;

    /**
     * Frontend task service.
     */
    @Autowired
    private Task task;

    /**
     * Frontend student service.
     */
    @Autowired
    private Student student;

    /**
     * Student service.
     */
    @Autowired
    private StudentService studentService;

    /**
     * List of sorting fields.
     */
    public static final String STUDENT_ROLE = "student";

    /**
     * Displaying student's index page.
     *
     * @param model
     * @param auth
     * @return view
     */
    @GetMapping("/")
    public String index(final Model model, final Authentication auth) {

        long studentId = getStudentId(auth);

        long groupId = studentService.getById(studentId).getGroupId();

        model.addAttribute(
                "studentId",
                studentId
        );

        model.addAttribute(
                "groupId",
                groupId
        );

        return "users/student/index";
    }

    /**
     * Displaying student's solutions.
     *
     * @param searchBy
     * @param isAsc
     * @param page
     * @param perPage
     * @param auth
     * @return modelAndView
     */
    @GetMapping("/solution/person/{studentId}")
    public ModelAndView solutionIndex(
            @RequestParam(name = "searchBy", required = false, defaultValue = "") final String searchBy,
            @RequestParam(name = "direct", required = false, defaultValue = "1") final boolean isAsc,
            @RequestParam(name = "page", required = false, defaultValue = "1") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            final Authentication auth
    ) {
        long studentId = getStudentId(auth);

        return solution.index(searchBy, isAsc, page, perPage, String.valueOf(studentId), STUDENT_ROLE);
    }

    /**
     * View solution by id.
     *
     * @param solutionId
     * @param auth
     * @return modelAndView
     */
    @GetMapping("/solution/view/{id}")
    public ModelAndView solutionView(final @PathVariable("id") Long solutionId,
                                     final Authentication auth) {
        return solution.view(solutionId, STUDENT_ROLE);
    }

    /**
     * View for creating new solution.
     *
     * @param model
     * @param auth
     * @return view
     */
    @GetMapping("/solution/create")
    public String solutionCreateForm(final Model model,
                                     final Authentication auth
    ) {

        long studentId = getStudentId(auth);

        return solution.createForm(model, studentId, STUDENT_ROLE);
    }

    /**
     * Processing of solution creation form.
     *
     * @param form
     * @param bindingResult
     * @param model
     * @param auth
     * @return redirect url
     */
    @PostMapping("/solution/create")
    public String solutionCreateFormProcessing(
            @Valid
            @ModelAttribute final SolutionCreateForm form,
            final BindingResult bindingResult,
            final Model model,
            final Authentication auth
    ) {

        long studentId = getStudentId(auth);

        return solution.createFormProcessing(form, bindingResult, model, studentId, STUDENT_ROLE);
    }

    /**
     * View for reuploading solution.
     *
     * @param solutionId
     * @param taskNum
     * @param auth
     * @return view
     */
    @GetMapping("/solution/reupload/{solutionId}/{taskNum}")
    public ModelAndView solutionReuploadForm(@PathVariable("solutionId") final Long solutionId,
                                             @PathVariable("taskNum") final Integer taskNum,
                                             final Authentication auth) {
        return solution.reuploadForm(solutionId, taskNum, STUDENT_ROLE);
    }

    /**
     * Processing of reuploading solution.
     *
     * @param form
     * @param solutionId
     * @param taskNum
     * @param bindingResult
     * @param model
     * @param auth
     * @return redirect url
     */
    @PostMapping("/solution/reupload/{solutionId}/{taskNum}")
    public String solutionReuploadFormProcessing(
            @Valid
            @ModelAttribute final SolutionUploadForm form,
            @PathVariable("solutionId") final Long solutionId,
            @PathVariable("taskNum") final Integer taskNum,
            final BindingResult bindingResult,
            final Model model,
            final Authentication auth
    ) {

        long studentId = getStudentId(auth);

        return solution.reuploadFormProcessing(form, solutionId, taskNum, bindingResult, model, studentId, STUDENT_ROLE);
    }

    /**
     * View for uploading solution.
     *
     * @param solutionId
     * @param taskNum
     * @param auth
     * @return view
     */
    @GetMapping("/solution/upload/{solutionId}/{taskNum}")
    public ModelAndView solutionUploadForm(@PathVariable("solutionId") final Long solutionId,
                                   @PathVariable("taskNum") final Integer taskNum,
                                   final Authentication auth) {

        long studentId = getStudentId(auth);

        return solution.uploadForm(solutionId, taskNum, studentId, STUDENT_ROLE);
    }


    /**
     * Processing of uploading form.
     *
     * @param form
     * @param solutionId
     * @param taskNum
     * @param bindingResult
     * @param model
     * @param auth
     * @return redirect url
     */
    @PostMapping("/solution/upload/{solutionId}/{taskNum}")
    public String solutionUploadFormProcessing(
            @Valid
            @ModelAttribute final SolutionUploadForm form,
            @PathVariable("solutionId") final Long solutionId,
            @PathVariable("taskNum") final Integer taskNum,
            final BindingResult bindingResult,
            final Model model,
            final Authentication auth
    ) {
        long studentId = getStudentId(auth);

        return solution.uploadFormProcessing(form, solutionId, taskNum, bindingResult, model, studentId, STUDENT_ROLE);
    }

    /**
     * Displaying tasks for group.
     *
     * @param groupId
     * @param model
     * @param auth
     * @return view
     */
    @GetMapping("/task/{groupId}")
    public String taskIndex(final Model model,
                            @PathVariable("groupId") final String groupId,
                            final Authentication auth) {
        return task.index(model, groupId, STUDENT_ROLE);
    }

    /**
     * View task by id.
     *
     * @param taskId
     * @param auth
     * @return modelAndView
     */
    @GetMapping("/task/view/{id}")
    public ModelAndView taskView(@PathVariable("id") final Long taskId,
                                 final Authentication auth) {
        return task.view(taskId, STUDENT_ROLE);
    }


    /**
     * View student by id.
     *
     * @param studentId
     * @return modelAndView
     */
    @GetMapping("/person/view/{id}")
    public ModelAndView personView(@PathVariable("id") final Long studentId) {
        return student.view(studentId, STUDENT_ROLE);
    }



    /**
     * Getting student Id.
     *
     * @param auth
     * @return studentId
     */
    private long getStudentId(final Authentication auth) {

        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetailsId) {
            return ((UserDetailsId) principal).getUserId();
        }
        throw new IllegalStateException("invalid auth.getPrincipal() object type");
    }
}
