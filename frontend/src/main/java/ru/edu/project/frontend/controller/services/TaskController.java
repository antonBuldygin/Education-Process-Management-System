package ru.edu.project.frontend.controller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.tasks.TaskForm;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.api.tasks.TaskService;
import ru.edu.project.frontend.controller.forms.tasks.TaskCreateForm;

import java.util.List;

@Component
public class TaskController {
    /**
     * Model's attribute for storing errors.
     */
    public static final String FORM_ERROR_ATTR = "errorsList";

    /**
     * Model's attribute for storing solutions.
     */
    public static final String SOLUTIONS_ATTR = "solutions";

    /**
     * Model's attribute for storing tasks.
     */
    public static final String TASKS_ATTR = "tasks";

    /**
     * Model's attribute for storing groups.
     */
    public static final String GROUPS_ATTR = "groups";

    /**
     * Model's attribute for storing group id.
     */
    public static final String GROUP_ID_ATTR = "groupId";

    /**
     * Model's attribute for storing role.
     */
    public static final String ROLE = "role";

    /**
     * Model's attribute for storing task record.
     */
    public static final String RECORD = "record";

    /**
     * Task service.
     */
    @Autowired
    private TaskService taskService;

    /**
     * Solution service.
     */
    @Autowired
    private SolutionService solutionService;

    /**
     * Group service.
     */
    @Autowired
    private GroupsService groupService;

    /**
     * Displaying tasks for group.
     *
     * @param groupId
     * @param model
     * @param role
     * @return view
     */
    public String index(final Model model, final String groupId, final String role) {

        model.addAttribute(
                ROLE,
                role
        );

        model.addAttribute(
                GROUP_ID_ATTR,
                groupId
        );

        if (groupId.equals("all")) {
            model.addAttribute(
                    TASKS_ATTR,
                    taskService.getAllTasks()
            );
        } else {
            model.addAttribute(
                    TASKS_ATTR,
                    taskService.getTasksByGroup(Long.valueOf(groupId))
            );
        }

        return "task/index";
    }

    /**
     * View task by id.
     *
     * @param taskId
     * @param role
     * @return modelAndView
     */
    public ModelAndView view(final Long taskId, final String role) {

        ModelAndView model = new ModelAndView("task/view");

        TaskInfo taskInfo = taskService.getById(taskId);

        model.addObject(
                RECORD,
                taskInfo
        );

        model.addObject(
                ROLE,
                role
        );

        return model;
    }

    /**
     * View deleting task by id.
     *
     * @param model
     * @param taskId
     * @param role
     * @return modelAndView
     */
    public String delete(final Model model,
                         final Long taskId,
                         final String role
    ) {

        List<SolutionInfo> solutionsByTask = solutionService.getSolutionsByTask(taskId);

        model.addAttribute(
                ROLE,
                role
        );

        if (solutionsByTask != null) {
            model.addAttribute(
                    SOLUTIONS_ATTR,
                    solutionsByTask
            );
            return "task/deleteError";
        }

        int deletedInfo = taskService.deleteById(taskId);

        return "redirect:/" + role + "/task/all";
    }



    /**
     * View for creating new task for group.
     *
     * @param role
     * @return view
     */
    public ModelAndView createForm(final String role) {

        ModelAndView model = new ModelAndView("task/create");

        model.addObject(
                ROLE,
                role
        );

        model.addObject(GROUPS_ATTR,
                groupService.getAllGroupsInfo());

        return model;
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
            final TaskCreateForm form,
            final BindingResult bindingResult,
            final Model model,
            final String role
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return createForm(role).getViewName();
        }

        model.addAttribute(
                ROLE,
                role
        );

        TaskInfo taskInfo = taskService.createTask(TaskForm.builder()
                        .groupId(form.getGroupId())
                        .num(form.getNum())
                        .title(form.getTitle())
                        .text(form.getText())
                        .startDate(form.getStartDate())
                        .endDate(form.getEndDate())
                        .build());

        return "redirect:/" + role + "/task/all/?created=" + taskInfo.getId();
    }


    /**
     * View for editing new task.
     *
     * @param taskId
     * @param role
     * @return view
     */
    public ModelAndView editForm(final Long taskId, final String role) {

        ModelAndView model = new ModelAndView("task/edit");

        TaskInfo taskInfo = taskService.getById(taskId);

        model.addObject(
                ROLE,
                role
        );

        model.addObject(
                RECORD,
                taskInfo
        );

        model.addObject(GROUPS_ATTR,
                groupService.getAllGroupsInfo());

        return model;
    }


    /**
     * Processing of edit form.
     *
     * @param taskId
     * @param form
     * @param bindingResult
     * @param model
     * @param role
     * @return redirect url
     */
    public String editFormProcessing(
            final TaskCreateForm form,
            final Long taskId,
            final BindingResult bindingResult,
            final Model model,
            final String role
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return createForm(role).getViewName();
        }

        model.addAttribute(
                ROLE,
                role
        );

        TaskInfo taskInfo = taskService.editTask(TaskForm.builder()
                        .id(taskId)
                        .groupId(form.getGroupId())
                        .num(form.getNum())
                        .title(form.getTitle())
                        .text(form.getText())
                        .startDate(form.getStartDate())
                        .endDate(form.getEndDate())
                        .build());

        return "redirect:/" + role + "/task/view/" + taskInfo.getId();
    }


}
