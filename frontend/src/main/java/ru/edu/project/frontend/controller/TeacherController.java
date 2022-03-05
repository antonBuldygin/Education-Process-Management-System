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
import ru.edu.project.frontend.controller.forms.solutions.SolutionVerifyForm;
import ru.edu.project.frontend.controller.forms.tasks.TaskCreateForm;
import ru.edu.project.frontend.controller.services.SolutionController;
import ru.edu.project.frontend.controller.services.TaskController;

import javax.validation.Valid;

@RequestMapping("/teacher")
@Controller
public class TeacherController {

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
    public static final String TEACHER_ROLE = "teacher";

    /**
     * Displaying teacher's index page.
     *
     * @param model
     * @return view
     */
    @GetMapping("/")
    public String index(final Model model) {

        return "teacher/index";
    }

    /**
     * Displaying solutions.
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
        return solution.index(searchBy, isAsc, page, perPage, TEACHER_ROLE);
    }

    /**
     * View solution by id.
     *
     * @param solutionId
     * @return modelAndView
     */
    @GetMapping("/solution/view/{id}")
    public ModelAndView solutionView(final @PathVariable("id") Long solutionId) {
        return solution.view(solutionId, TEACHER_ROLE);
    }


    /**
     * View for taking solution for review.
     *
     * @param model
     * @param solutionId
     * @return view
     */
    @GetMapping("/solution/review/{solutionId}")
    public String solutionReviewForm(final Model model, @PathVariable("solutionId") final Long solutionId) {
        return solution.reviewForm(model, solutionId, TEACHER_ROLE);
    }

    /**
     * View for verifying solution.
     *
     * @param solutionId
     * @param taskNum
     * @return view
     */
    @GetMapping("/solution/verify/{solutionId}/{taskNum}")
    public ModelAndView solutionVerifyForm(@PathVariable("solutionId") final Long solutionId,
                                           @PathVariable("taskNum") final Integer taskNum) {
        return solution.verifyForm(solutionId, taskNum, TEACHER_ROLE);
    }

    /**
     * Processing of verifying form.
     *
     * @param form
     * @param solutionId
     * @param taskNum
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/solution/verify/{solutionId}/{taskNum}")
    public String solutionVerifyFormProcessing(
            @Valid
            @ModelAttribute final SolutionVerifyForm form,
            @PathVariable("solutionId") final Long solutionId,
            @PathVariable("taskNum") final Integer taskNum,
            final BindingResult bindingResult,
            final Model model
    ) {
        return solution.verifyFormProcessing(form, solutionId, taskNum, bindingResult, model, TEACHER_ROLE);
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
        return task.index(model, groupId, TEACHER_ROLE);
    }

    /**
     * View task by id.
     *
     * @param taskId
     * @return modelAndView
     */
    @GetMapping("/task/view/{id}")
    public ModelAndView taskView(@PathVariable("id") final Long taskId) {
        return task.view(taskId, TEACHER_ROLE);
    }

    /**
     * View deleting task by id.
     *
     * @param groupId
     * @param model
     * @param taskId
     * @return modelAndView
     */
    @GetMapping("/task/delete/{id}/{groupId}")
    public String taskDelete(final Model model,
                         @PathVariable("id") final Long taskId,
                         @PathVariable("groupId") final Long groupId) {

        return task.delete(model, taskId, groupId, TEACHER_ROLE);
    }


    /**
     * View for creating new task for group.
     *
     * @param groupId
     * @param model
     * @return view
     */
    @GetMapping("/task/create/{groupId}")
    public String taskCreateForm(final Model model,
                             @PathVariable("groupId") final Long groupId) {
        return task.createForm(model, groupId, TEACHER_ROLE);
    }

    /**
     * Processing of task creation form.
     *
     * @param form
     * @param groupId
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/task/create/{groupId}")
    public String taskCreateFormProcessing(
            @Valid
            @ModelAttribute final TaskCreateForm form,
            @PathVariable("groupId") final Long groupId,
            final BindingResult bindingResult,
            final Model model
    ) {
        return task.createFormProcessing(form, groupId, bindingResult, model, TEACHER_ROLE);
    }

    /**
     * View for editing new task.
     *
     * @param taskId
     * @return view
     */
    @GetMapping("/task/edit/{taskId}")
    public ModelAndView taskEditForm(@PathVariable("taskId") final Long taskId) {
        return task.editForm(taskId, TEACHER_ROLE);
    }

    /**
     * Processing of edit form.
     *
     * @param taskId
     * @param form
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/task/edit/{taskId}")
    public String taskEditFormProcessing(
            @Valid
            @ModelAttribute final TaskCreateForm form,
            @PathVariable("taskId") final Long taskId,
            final BindingResult bindingResult,
            final Model model
    ) {
        return task.editFormProcessing(form, taskId, bindingResult, model, TEACHER_ROLE);
    }

}
