package ru.edu.project.frontend.controller;

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
import ru.edu.project.frontend.controller.forms.groups.GroupsCreateForm;
import ru.edu.project.frontend.controller.services.GroupController;
import ru.edu.project.frontend.controller.services.SolutionController;
import ru.edu.project.frontend.controller.services.TaskController;

import javax.validation.Valid;

@RequestMapping("/admin")
@Controller
public class AdminController {

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
     * Frontend group controller.
     */
    @Autowired
    private GroupController group;

    /**
     * List of sorting fields.
     */
    public static final String ADMIN_ROLE = "admin";

    /**
     * Displaying admin's index page.
     *
     * @param model
     * @return view
     */
    @GetMapping("/")
    public String index(final Model model) {
         return "admin/index";
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
        return solution.index(searchBy, isAsc, page, perPage, ADMIN_ROLE);
    }

    /**
     * Displaying tasks for group.
     *
     * @param groupId
     * @param model
     * @return view
     */
    @GetMapping("/task")
    public String taskIndex(final Model model,
                            @RequestParam(name = "groupId", required = false, defaultValue = "all") final String groupId) {
        return task.index(model, groupId, ADMIN_ROLE);
    }

    /**
     * View task by id.
     *
     * @param taskId
     * @return modelAndView
     */
    @GetMapping("/task/view/{id}")
    public ModelAndView taskView(@PathVariable("id") final Long taskId) {
        return task.view(taskId, ADMIN_ROLE);
    }


    /**
     * Displaying group's index page.
     *
     * @param model
     * @return view
     */
    @GetMapping("/groups")
    public String groupIndex(final Model model) {
        return group.index(model);
    }

    /**
     * View group by id.
     *
     * @param id
     * @return modelAndView
     */
    @GetMapping("/groups/view/{id}")
    public ModelAndView groupView(final @PathVariable("id") long id) {
        return group.view(id);
    }

    /**
     * Deleting group.
     *
     * @param id
     * @return view
     */
    @GetMapping("/groups/delete/{id}")
    public String groupDeleteForm(@PathVariable("id") final long id) {
        return group.deleteForm(id);
    }

    /**
     * View for creating new group.
     *
     * @param model
     * @return modelAndView
     */
    @GetMapping("/groups/create")
    public String groupCreateForm(final Model model) {
        return group.createForm(model);
    }

    /**
     * Processing of creation form.
     *
     * @param form
     * @param bindingResult результат валидации формы
     * @param model
     * @param auth
     * @return redirect url
     */
    @PostMapping("/groups/create")
    public String groupCreateFormProcessing(
            @Valid
            @ModelAttribute final GroupsCreateForm form,
            final BindingResult bindingResult,
            final Model model,
            final Authentication auth
    ) {
        return group.createFormProcessing(form, bindingResult, model);
    }


}
