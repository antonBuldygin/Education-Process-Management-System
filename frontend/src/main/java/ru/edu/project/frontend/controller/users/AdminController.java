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
import ru.edu.project.frontend.controller.forms.groups.GroupsCreateForm;
import ru.edu.project.frontend.controller.forms.students.StudentCreateForm;
import ru.edu.project.frontend.controller.services.Group;
import ru.edu.project.frontend.controller.services.Solution;
import ru.edu.project.frontend.controller.services.Student;
import ru.edu.project.frontend.controller.services.Task;

import javax.validation.Valid;

@RequestMapping("/admin")
@Controller
public class AdminController {

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
     * Frontend group service.
     */
    @Autowired
    private Group group;

    /**
     * Frontend student service.
     */
    @Autowired
    private Student student;

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
         return "users/admin/index";
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
        return solution.index(searchBy, isAsc, page, perPage, "all", ADMIN_ROLE);
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
        return solution.view(solutionId, ADMIN_ROLE);
    }

    /**
     * Displaying student's solutions.
     *
     * @param searchBy
     * @param isAsc
     * @param page
     * @param perPage
     * @param studentId
     * @return modelAndView
     */
    @GetMapping("/solution/person/{studentId}")
    public ModelAndView solutionByStudentIndex(
            @RequestParam(name = "searchBy", required = false, defaultValue = "") final String searchBy,
            @RequestParam(name = "direct", required = false, defaultValue = "1") final boolean isAsc,
            @RequestParam(name = "page", required = false, defaultValue = "1") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @PathVariable("studentId") final String studentId
    ) {
        return solution.index(searchBy, isAsc, page, perPage, studentId, ADMIN_ROLE);
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
     * @return redirect url
     */
    @PostMapping("/groups/create")
    public String groupCreateFormProcessing(
            @Valid
            @ModelAttribute final GroupsCreateForm form,
            final BindingResult bindingResult,
            final Model model
    ) {
        return group.createFormProcessing(form, bindingResult, model);
    }

    /**
     * Displaying all student's info.
     *
     * @param model
     * @return view
     */
    @GetMapping("/person/all")
    public String personIndex(final Model model) {
        return student.index(model, ADMIN_ROLE);
    }

    /**
     * View student by id.
     *
     * @param studentId
     * @return modelAndView
     */
    @GetMapping("/person/view/{id}")
    public ModelAndView personView(@PathVariable("id") final Long studentId) {
        return student.view(studentId, ADMIN_ROLE);
    }

    /**
     * View deleting student by id.
     *
     * @param model
     * @param studentId
     * @return modelAndView
     */
    @GetMapping("/person/delete/{id}")
    public String personDelete(final Model model,
                         @PathVariable("id") final Long studentId
    ) {
        return student.delete(model, studentId, ADMIN_ROLE);
    }

    /**
     * View for creating new student.
     *
     * @param model
     * @return view
     */
    @GetMapping("/person/create")
    public String personCreateForm(final Model model) {
        return student.createForm(model, ADMIN_ROLE);
    }

    /**
     * Processing of creation form.
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/person/create")
    public String personCreateFormProcessing(
            @Valid
            @ModelAttribute final StudentCreateForm form,
            final BindingResult bindingResult,
            final Model model
    ) {
        return student.createFormProcessing(form, bindingResult, model, ADMIN_ROLE);
    }

    /**
     * View for editing student.
     *
     * @param studentId
     * @return view
     */
    @GetMapping("/person/edit/{studentId}")
    public ModelAndView personEditForm(@PathVariable("studentId") final Long studentId) {
        return student.editForm(studentId, ADMIN_ROLE);
    }

    /**
     * Processing of edit form.
     *
     * @param studentId
     * @param form
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/person/edit/{studentId}")
    public String personEditFormProcessing(
            @Valid
            @ModelAttribute final StudentCreateForm form,
            @PathVariable("studentId") final Long studentId,
            final BindingResult bindingResult,
            final Model model
    ) {
        return student.editFormProcessing(form, studentId, bindingResult, model, ADMIN_ROLE);
    }
}
