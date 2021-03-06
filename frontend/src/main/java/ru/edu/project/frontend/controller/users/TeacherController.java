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
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.teachers.TeacherService;
import ru.edu.project.frontend.controller.forms.solutions.SolutionVerifyingForm;
import ru.edu.project.frontend.controller.forms.tasks.TaskCreateForm;
import ru.edu.project.frontend.controller.services.Group;
import ru.edu.project.frontend.controller.services.Solution;
import ru.edu.project.frontend.controller.services.Student;
import ru.edu.project.frontend.controller.services.Task;

import javax.validation.Valid;

@RequestMapping("/teacher")
@Controller
public class TeacherController {

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
    public static final String TEACHER_ROLE = "teacher";


    /**
     * Имя атрибута с заявками.
     */
    public static final String REQUESTS = "requests";

    /**
     * Атрибут модели для хранения списка доступных услуг.
     */
    public static final String JOBS_ATTR = "teachers";

    /**
     * Имя атрибута заявки.
     */
    public static final String RECORD_ATTR = "record";

    /**
     * Имя атрибута статусов заявки.
     */
    public static final String STATUSES_ATTR = "statuses";

    /**
     * Зависимость на сервис заявок.
     */
    @Autowired
    private GroupsService groupsService;

    /**
     * Ссылка на сервис услуг.
     */
    @Autowired
    private TeacherService teacherService;

    /**
     * Выбор групп по учителю.
     *
     * @param searchId
     * @return modelAndView
     */
    @GetMapping("/ts")
    public ModelAndView index(
            @RequestParam(name = "id", required = false, defaultValue = "1") final int searchId
    ) {

        ModelAndView model = new ModelAndView("teacher/index");
        model.addObject(JOBS_ATTR, teacherService.getAvailable());
        model.addObject(REQUESTS,
                groupsService.getAllGroupsByTeacher(searchId));
        return model;
    }

    /**
     * Просмотр заявки.
     *
     * @param id
     * @return modelAndView
     */
    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") final Long id) {

        ModelAndView modelAndView = new ModelAndView("teacher/view");
        modelAndView.addObject(RECORD_ATTR, groupsService.getDetailedInfo(id));
        modelAndView.addObject(STATUSES_ATTR, ManagerController.RequestStatus.values());

        groupsService.getDetailedInfo(id).getStatus().getMessage();
        return modelAndView;
    }

    /**
     * Displaying teacher's index page.
     *
     * @param model
     * @return view
     */
    @GetMapping("/")
    public String index(final Model model) {

        return "users/teacher/index";
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
        return solution.index(searchBy, isAsc, page, perPage, "all", TEACHER_ROLE);
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
        return solution.index(searchBy, isAsc, page, perPage, studentId, TEACHER_ROLE);
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
    public String solutionReviewForm(final Model model,
                                     @PathVariable("solutionId") final Long solutionId) {
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
            @ModelAttribute final SolutionVerifyingForm form,
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
    public String taskIndex(final Model model,
                            @PathVariable("groupId") final String groupId) {
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
     * @param model
     * @param taskId
     * @return modelAndView
     */
    @GetMapping("/task/delete/{id}")
    public String taskDelete(final Model model,
                         @PathVariable("id") final Long taskId) {

        return task.delete(model, taskId, TEACHER_ROLE);
    }


    /**
     * View for creating new task for group.
     *
     * @return ModelAndView
     */
    @GetMapping("/task/create")
    public ModelAndView taskCreateForm() {
        return task.createForm(TEACHER_ROLE);
    }

    /**
     * Processing of task creation form.
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/task/create")
    public String taskCreateFormProcessing(
            @Valid
            @ModelAttribute final TaskCreateForm form,
            final BindingResult bindingResult,
            final Model model
    ) {
        return task.createFormProcessing(form, bindingResult, model, TEACHER_ROLE);
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

    /**
     * Displaying group's index page.
     *
     * @param model
     * @return view
     */
    @GetMapping("/group/")
    public String groupIndex(final Model model) {
        return group.index(model);
    }

    /**
     * Displaying all student's info.
     *
     * @param model
     * @return view
     */
    @GetMapping("/person/all")
    public String personIndex(final Model model) {
        return student.index(model, TEACHER_ROLE);
    }

    /**
     * View student by id.
     *
     * @param studentId
     * @return modelAndView
     */
    @GetMapping("/person/view/{id}")
    public ModelAndView personView(@PathVariable("id") final Long studentId) {
        return student.view(studentId, TEACHER_ROLE);
    }

}
