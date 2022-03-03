package ru.edu.project.frontend.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.tasks.TaskForm;
import ru.edu.project.backend.api.tasks.TaskInfo;
import ru.edu.project.backend.api.tasks.TaskService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/task")
@Controller
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
     * Displaying tasks for group.
     *
     * @param groupId
     * @param model
     * @return view
     */
    @GetMapping("/{groupId}")
    public String index(final Model model, @PathVariable("groupId") final Long groupId) {

        model.addAttribute(
                "groupId",
                groupId
        );

        model.addAttribute(
                TASKS_ATTR,
                taskService.getTasksByGroup(groupId)
        );

        return "task/index";
    }

    /**
     * View task by id.
     *
     * @param taskId
     * @return modelAndView
     */
    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable("id") final Long taskId) {

        ModelAndView model = new ModelAndView("task/view");

        TaskInfo taskInfo = taskService.getById(taskId);

        model.addObject(
                "record",
                taskInfo
        );

        return model;
    }

    /**
     * View deleting task by id.
     *
     * @param groupId
     * @param model
     * @param taskId
     * @return modelAndView
     */
    @GetMapping("/delete/{id}/{groupId}")
    public String delete(final Model model,
                         @PathVariable("id") final Long taskId,
                         @PathVariable("groupId") final Long groupId) {

        List<SolutionInfo> solutionsByTask = solutionService.getSolutionsByTask(taskId);

        if (solutionsByTask != null) {
            model.addAttribute(
                    "groupId",
                    groupId
            );
            model.addAttribute(
                    SOLUTIONS_ATTR,
                    solutionsByTask
            );
            return "task/deleteError";
        }

        int deletedInfo = taskService.deleteById(taskId);

        return "redirect:/task/" + groupId;
    }



    /**
     * View for creating new task for group.
     *
     * @param groupId
     * @param model
     * @return view
     */
    @GetMapping("/create/{groupId}")
    public String createForm(final Model model,
                             @PathVariable("groupId") final Long groupId) {

        model.addAttribute("groupId",
                groupId);
        return "task/create";
    }


    /**
     * Processing of creation form.
     *
     * @param form
     * @param groupId
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/create/{groupId}")
    public String createFormProcessing(
            @Valid
            @ModelAttribute final CreateForm form,
            @PathVariable("groupId") final Long groupId,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return createForm(model, groupId);
        }

        TaskInfo taskInfo = taskService.createTask(TaskForm.builder()
                        .groupId(groupId)
                        .num(form.getNum())
                        .title(form.getTitle())
                        .text(form.getText())
                        .startDate(form.getStartDate())
                        .endDate(form.getEndDate())
                        .build());

        return "redirect:/task/" + groupId + "/?created=" + taskInfo.getId();
    }

    @Getter
    @Setter
    public static class CreateForm {

        /**
         * Format for date parsing.
         */
        private static final DateFormat FORMAT;

        static {
            FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        }

        /**
         * GroupId.
         */
        private Long groupId;

        /**
         * Number.
         */
        @NotNull
        private Integer num;

        /**
         * Task title.
         */
        @NotNull
        private String title;

        /**
         * Task text.
         */
        private String text;

        /**
         * Start date of task.
         */
        @NotEmpty
        private String startDate;

        /**
         * End date of task.
         */
        @NotEmpty
        private String endDate;

        /**
         * Getting calendar object for startDate.
         *
         * @return Timestamp
         */
        @SneakyThrows
        public Timestamp getStartDate() {
            Date parsed = FORMAT.parse(startDate);
            return new Timestamp(parsed.getTime());
        }

        /**
         * Getting calendar object for endDate.
         *
         * @return Timestamp
         */
        @SneakyThrows
        public Timestamp getEndDate() {
            Date parsed = FORMAT.parse(endDate);
            return new Timestamp(parsed.getTime());
        }
    }

    /**
     * View for editing new task.
     *
     * @param taskId
     * @return view
     */
    @GetMapping("/edit/{taskId}")
    public ModelAndView editForm(@PathVariable("taskId") final Long taskId) {

        ModelAndView model = new ModelAndView("task/edit");

        TaskInfo taskInfo = taskService.getById(taskId);

        model.addObject(
                "record",
                taskInfo
        );

        return model;
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
    @PostMapping("/edit/{taskId}")
    public String editFormProcessing(
            @Valid
            @ModelAttribute final CreateForm form,
            @PathVariable("taskId") final Long taskId,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return createForm(model, taskId);
        }

        TaskInfo taskInfo = taskService.editTask(TaskForm.builder()
                        .id(taskId)
                        .groupId(form.getGroupId())
                        .num(form.getNum())
                        .title(form.getTitle())
                        .text(form.getText())
                        .startDate(form.getStartDate())
                        .endDate(form.getEndDate())
                        .build());

        return "redirect:/task/edit/" + taskInfo.getId();
    }


}
