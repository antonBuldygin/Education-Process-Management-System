package ru.edu.project.frontend.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.backend.api.solutions.SolutionForm;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionReviewForm;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.tasks.TaskService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@RequestMapping("/solution")
@Controller
public class SolutionController {

    /**
     * Model's attribute for storing errors.
     */
    public static final String FORM_ERROR_ATTR = "errorsList";

    /**
     * Model's attribute for storing tasks.
     */
    public static final String TASKS_ATTR = "tasks";

    /**
     * Model's attribute for storing solutions.
     */
    public static final String SOLUTIONS_ATTR = "solutions";

    /**
     * Solution service.
     */
    @Autowired
    private SolutionService solutionService;

    /**
     * Task service.
     */
    @Autowired
    private TaskService taskService;

    /**
     * Displaying student's solutions.
     *
     * @param model
     * @return view
     */
    @GetMapping("/")
    public String index(final Model model) {
        long studentId = 1; //todo в дальнейшим заменим на id пользователя

        model.addAttribute(
                SOLUTIONS_ATTR,
                solutionService.getSolutionsByStudent(studentId)
        );

        return "solution/index";
    }




    /**
     * View solution by id.
     *
     * @param solutionId
     * @return modelAndView
     */
    @GetMapping("/view/{id}")
    public ModelAndView view(final @PathVariable("id") Long solutionId) {
        long studentId = 1; //todo student id

        ModelAndView model = new ModelAndView("solution/view");

        SolutionInfo detailedInfo = solutionService.getDetailedInfo(solutionId);

        if (detailedInfo == null) {
            model.setStatus(HttpStatus.NOT_FOUND);
            model.setViewName("solution/viewNotFound");
            return model;
        }

        model.addObject(
                "record",
                detailedInfo
        );

        return model;
    }


    /**
     * View for creating new solution.
     *
     * @param model
     * @return view
     */
    @GetMapping("/create")
    public String createForm(final Model model) {
        long studentId = 1; //todo в дальнейшим заменим на id пользователя
        long groupId = 1; //todo добавить получение группы по студенту
        model.addAttribute(TASKS_ATTR, taskService.getTasksByGroup(1)); //todo taskService.getAvailable(groupId));
        return "solution/create";
    }


    /**
     * Processing of creation form.
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return redirect url
     */
    @PostMapping("/create")
    public String createFormProcessing(
            @Valid
            @ModelAttribute final CreateForm form,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return createForm(model);
        }

        final long studentId = 1; //todo student id

        SolutionInfo solution = solutionService.takeToWork(studentId, form.getTask());

        return "redirect:/solution/?created=" + solution.getId();
    }

    @Getter
    @Setter
    public static class CreateForm {

        /**
         * Task.
         */
        @NotNull
        private Long task;

    }

    /**
     * View for reuploading solution.
     *
     * @param solutionId
     * @param taskNum
     * @return view
     */
    @GetMapping("/reupload/{solutionId}/{taskNum}")
    public ModelAndView reuploadForm(@PathVariable("solutionId") final Long solutionId,
                                   @PathVariable("taskNum") final Integer taskNum) {

        ModelAndView model = new ModelAndView("solution/upload");

        SolutionInfo detailedInfo = solutionService.getDetailedInfo(solutionId);

        model.addObject(
                "taskNum",
                taskNum
        );
        model.addObject(
                "record",
                detailedInfo
        );

        return model;
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
    @PostMapping("/reupload/{solutionId}/{taskNum}")
    public String reuploadFormProcessing(
            @Valid
            @ModelAttribute final UploadForm form,
            @PathVariable("solutionId") final Long solutionId,
            @PathVariable("taskNum") final Integer taskNum,
            final BindingResult bindingResult,
            final Model model
    ) {
        return uploadFormProcessing(form, solutionId, taskNum, bindingResult, model);
    }

    /**
     * View for uploading solution.
     *
     * @param solutionId
     * @param taskNum
     * @return view
     */
    @GetMapping("/upload/{solutionId}/{taskNum}")
    public ModelAndView uploadForm(@PathVariable("solutionId") final Long solutionId,
                                   @PathVariable("taskNum") final Integer taskNum) {

        ModelAndView model = new ModelAndView("solution/upload");

        model.addObject(
                "taskNum",
                taskNum
        );

        return model;
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
    @PostMapping("/upload/{solutionId}/{taskNum}")
    public String uploadFormProcessing(
            @Valid
            @ModelAttribute final UploadForm form,
            @PathVariable("solutionId") final Long solutionId,
            @PathVariable("taskNum") final Integer taskNum,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return uploadForm(solutionId, taskNum).getViewName();
        }

        final long studentId = 1; //todo student id

        SolutionInfo solution = solutionService.uploadSolution(SolutionForm.builder()
                .solutionId(solutionId)
                .studentId(studentId)
                .text(form.getText())
                .comment(form.getComment())
                .build());

        return "redirect:/solution/view/" + solution.getId();
    }

    @Getter
    @Setter
    public static class UploadForm {

        /**
         * Text.
         */
        @NotNull
        private String text;

        /**
         * Comment.
         */
        private String comment;

    }

    /**
     * View for taking solution for review.
     *
     * @param model
     * @param solutionId
     * @return view
     */
    @GetMapping("/review/{solutionId}")
    public String reviewForm(final Model model, @PathVariable("solutionId") final Long solutionId) {

        solutionService.takeForReview(solutionId);

        return "redirect:/solution/view/" + solutionId;
    }

    /**
     * View for verifying solution.
     *
     * @param solutionId
     * @param taskNum
     * @return view
     */
    @GetMapping("/verify/{solutionId}/{taskNum}")
    public ModelAndView verifyForm(@PathVariable("solutionId") final Long solutionId,
                                   @PathVariable("taskNum") final Integer taskNum) {

        ModelAndView model = new ModelAndView("solution/verify");

        model.addObject(
                "taskNum",
                taskNum
        );

        return model;
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
    @PostMapping("/verify/{solutionId}/{taskNum}")
    public String verifyFormProcessing(
            @Valid
            @ModelAttribute final VerifyForm form,
            @PathVariable("solutionId") final Long solutionId,
            @PathVariable("taskNum") final Integer taskNum,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return verifyForm(solutionId, taskNum).getViewName();
        }

        final long studentId = 1; //todo student id

        SolutionInfo solution = solutionService.verify(SolutionReviewForm.builder()
                .solutionId(solutionId)
                .studentId(studentId)
                .score(form.getScore())
                .comment(form.getComment())
                .build());

        return "redirect:/solution/view/" + solution.getId();
    }

    @Getter
    @Setter
    public static class VerifyForm {

        /**
         * Max score value.
         */
        private static final int MAX_SCORE = 100;

        /**
         * Min score value.
         */
        private static final int MIN_SCORE = 0;

        /**
         * Score.
         */
        @NotNull
        @Max(MAX_SCORE)
        @Min(MIN_SCORE)
        private Integer score;

        /**
         * Comment.
         */
        private String comment;

    }
}
