package ru.edu.project.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.edu.project.frontend.controller.forms.solutions.SolutionCreateForm;
import ru.edu.project.frontend.controller.forms.solutions.SolutionUploadForm;
import ru.edu.project.frontend.controller.services.SolutionController;
import ru.edu.project.frontend.controller.services.TaskController;

import javax.validation.Valid;

@RequestMapping("/student")
@Controller
public class StudentController {

    /**
     * Frontend solution controller.
     */
    @Autowired
    private SolutionController solution;

    /**
     * Frontend task controller.
     */
    @Autowired
    private TaskController task;

    /**
     * List of sorting fields.
     */
    public static final String STUDENT_ROLE = "student";

    /**
     * Displaying student's index page.
     *
     * @param model
     * @return view
     */
    @GetMapping("/")
    public String index(final Model model) {
        long groupId = 1; //todo заменить на получение номера группы

        model.addAttribute(
                "groupId",
                groupId
        );

        return "student/index";
    }

    /**
     * Displaying student's solutions.
     *
     * @param searchBy
     * @param isAsc
     * @param page
     * @param perPage
     * @return modelAndView
     */
    @GetMapping("/solution")
    public ModelAndView solutionIndex(
            @RequestParam(name = "searchBy", required = false, defaultValue = "") final String searchBy,
            @RequestParam(name = "direct", required = false, defaultValue = "1") final boolean isAsc,
            @RequestParam(name = "page", required = false, defaultValue = "1") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage
    ) {
        return solution.index(searchBy, isAsc, page, perPage, STUDENT_ROLE);
    }

    /**
     * View solution by id.
     *
     * @param solutionId
     * @return modelAndView
     */
    @GetMapping("/solution/view/{id}")
    public ModelAndView solutionView(final @PathVariable("id") Long solutionId) {
        return solution.view(solutionId, STUDENT_ROLE);
    }

    /**
     * View for creating new solution.
     *
     * @param model
     * @return view
     */
    @GetMapping("/solution/create")
    public String solutionCreateForm(final Model model) {
        return solution.createForm(model, STUDENT_ROLE);
    }

    /**
     * Processing of solution creation form.
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/solution/create")
    public String solutionCreateFormProcessing(
            @Valid
            @ModelAttribute final SolutionCreateForm form,
            final BindingResult bindingResult,
            final Model model
    ) {
        return solution.createFormProcessing(form, bindingResult, model, STUDENT_ROLE);
    }

    /**
     * View for reuploading solution.
     *
     * @param solutionId
     * @param taskNum
     * @return view
     */
    @GetMapping("/solution/reupload/{solutionId}/{taskNum}")
    public ModelAndView solutionReuploadForm(@PathVariable("solutionId") final Long solutionId,
                                             @PathVariable("taskNum") final Integer taskNum) {
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
     * @return redirect url
     */
    @PostMapping("/solution/reupload/{solutionId}/{taskNum}")
    public String reuploadFormProcessing(
            @Valid
            @ModelAttribute final SolutionUploadForm form,
            @PathVariable("solutionId") final Long solutionId,
            @PathVariable("taskNum") final Integer taskNum,
            final BindingResult bindingResult,
            final Model model
    ) {
        return reuploadFormProcessing(form, solutionId, taskNum, bindingResult, model);
    }

    /**
     * View for uploading solution.
     *
     * @param solutionId
     * @param taskNum
     * @return view
     */
    @GetMapping("/solution/upload/{solutionId}/{taskNum}")
    public ModelAndView uploadForm(@PathVariable("solutionId") final Long solutionId,
                                   @PathVariable("taskNum") final Integer taskNum) {

        return solution.uploadForm(solutionId, taskNum, STUDENT_ROLE);
    }


    /**
     * Processing of uploading form.
     *
     * @param form
     * @param solutionId
     * @param taskNum
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/solution/upload/{solutionId}/{taskNum}")
    public String uploadFormProcessing(
            @Valid
            @ModelAttribute final SolutionUploadForm form,
            @PathVariable("solutionId") final Long solutionId,
            @PathVariable("taskNum") final Integer taskNum,
            final BindingResult bindingResult,
            final Model model
    ) {
        return solution.uploadFormProcessing(form, solutionId, taskNum, bindingResult, model, STUDENT_ROLE);
    }

    /**
     * Displaying tasks for group.
     *
     * @param groupId
     * @param model
     * @return view
     */
    @GetMapping("/task/{groupId}")
    public String taskIndex(final Model model, @PathVariable("groupId") final String groupId) {
        return task.index(model, groupId, STUDENT_ROLE);
    }

    /**
     * View task by id.
     *
     * @param taskId
     * @return modelAndView
     */
    @GetMapping("/task/view/{id}")
    public ModelAndView taskView(@PathVariable("id") final Long taskId) {
        return task.view(taskId, STUDENT_ROLE);
    }

}
