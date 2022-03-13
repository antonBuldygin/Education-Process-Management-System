package ru.edu.project.frontend.controller.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionForm;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.solutions.SolutionVerifyForm;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.students.StudentService;
import ru.edu.project.backend.api.tasks.TaskService;
import ru.edu.project.frontend.controller.forms.solutions.SolutionCreateForm;
import ru.edu.project.frontend.controller.forms.solutions.SolutionUploadForm;
import ru.edu.project.frontend.controller.forms.solutions.SolutionVerifyingForm;


import java.util.Arrays;
import java.util.Optional;

@Component
public class Solution {

    /**
     * List of sorting fields.
     */
    public static final String ORDER_FIELDS = "orderFields";

    /**
     * Current sorting field.
     */
    public static final String ORDER_BY_FIELD = "orderByField";

    /**
     * Sorting direction.
     */
    public static final String IS_ASC = "isAsc";

    /**
     * Solution.
     */
    public static final String RECORD_ATTR = "record";

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
    public static final String SOLUTIONS_ATTR = "solutionsPage";

    /**
     * Model's attribute for storing role.
     */
    public static final String ROLE = "role";

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
     * Task service.
     */
    @Autowired
    private StudentService studentService;

    /**
     * Displaying student's solutions.
     *
     * @param searchBy
     * @param isAsc
     * @param page
     * @param perPage
     * @param role
     * @param studentId
     * @return modelAndView
     */
    public ModelAndView index(final String searchBy,
                              final boolean isAsc,
                              final int page,
                              final int perPage,
                              final String studentId,
                              final String role
    ) {

        ModelAndView model = new ModelAndView("solution/index");
        SearchFields searchByField = SearchFields.byString(searchBy);

        model.addObject(
                ROLE,
                role
        );

        if ("all".equals(studentId)) {
            model.addObject(
                    SOLUTIONS_ATTR,
                    solutionService.searchSolutions(
                            SolutionSearch.by(null,
                                    searchByField.getField(),
                                    isAsc,
                                    page - 1,
                                    perPage)
                    )
            );
        } else {
            model.addObject(
                    SOLUTIONS_ATTR,
                    solutionService.searchSolutions(
                            SolutionSearch.by(
                                    Long.valueOf(studentId),
                                    searchByField.getField(),
                                    isAsc,
                                    page - 1,
                                    perPage)
                    )
            );
        }

        model.addObject(ORDER_FIELDS, SearchFields.values());
        model.addObject(ORDER_BY_FIELD, searchByField);
        model.addObject(IS_ASC, isAsc);

        return model;
    }



    /**
     * View solution by id.
     *
     * @param solutionId
     * @param role
     * @return modelAndView
     */
    public ModelAndView view(final Long solutionId, final String role) {

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

        model.addObject(
                ROLE,
                role
        );

        return model;
    }



    /**
     * View for creating new solution.
     *
     * @param model
     * @param role
     * @param studentId
     * @return view
     */
    public String createForm(final Model model,
                             final Long studentId,
                             final String role) {

        StudentInfo studentInfo = studentService.getById(studentId);

        model.addAttribute(
                "studentId",
                studentId
        );

        model.addAttribute(
                ROLE,
                role
        );

        model.addAttribute(TASKS_ATTR,
                taskService.getTasksByGroup(studentInfo.getGroupId())); //todo taskService.getAvailable(groupId));

        return "solution/create";
    }


    /**
     * Processing of creation form.
     *
     * @param form
     * @param bindingResult
     * @param model
     * @param role
     * @param studentId
     * @return redirect url
     */
    public String createFormProcessing(
            final SolutionCreateForm form,
            final BindingResult bindingResult,
            final Model model,
            final Long studentId,
            final String role
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return createForm(model, studentId, role);
        }

        model.addAttribute(
                ROLE,
                role
        );

        SolutionInfo solution = solutionService.takeToWork(studentId, form.getTask());

        return "redirect:/" + role + "/solution/view/" + solution.getId();
    }

    /**
     * View for uploading solution.
     *
     * @param solutionId
     * @param taskNum
     * @param studentId
     * @param role
     * @return view
     */
    public ModelAndView uploadForm(final Long solutionId,
                                   final Integer taskNum,
                                   final Long studentId,
                                   final String role
    ) {

        ModelAndView model = new ModelAndView("solution/upload");

        model.addObject(
                ROLE,
                role
        );

        model.addObject(
                "studentId",
                studentId
        );

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
     * @param role
     * @param studentId
     * @return redirect url
     */
    public String uploadFormProcessing(
            final SolutionUploadForm form,
            final Long solutionId,
            final Integer taskNum,
            final BindingResult bindingResult,
            final Model model,
            final Long studentId,
            final String role
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return uploadForm(solutionId, taskNum, studentId, role).getViewName();
        }

        model.addAttribute(
                ROLE,
                role
        );

        SolutionInfo solution = solutionService.uploadSolution(SolutionForm.builder()
                .solutionId(solutionId)
                .studentId(studentId)
                .text(form.getText())
                .comment(form.getComment())
                .build());

        return "redirect:/" + role + "/solution/view/" + solution.getId();
    }


    /**
     * View for reuploading solution.
     *
     * @param solutionId
     * @param taskNum
     * @param role
     * @return view
     */
    public ModelAndView reuploadForm(final Long solutionId,
                                     final Integer taskNum,
                                     final String role
    ) {

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

        model.addObject(
                ROLE,
                role
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
     * @param studentId
     * @param role
     * @return redirect url
     */
    public String reuploadFormProcessing(
            final SolutionUploadForm form,
            final Long solutionId,
            final Integer taskNum,
            final BindingResult bindingResult,
            final Model model,
            final Long studentId,
            final String role
    ) {
        return uploadFormProcessing(form, solutionId, taskNum, bindingResult, model, studentId, role);
    }


    /**
     * View for taking solution for review.
     *
     * @param model
     * @param solutionId
     * @param role
     * @return view
     */
    public String reviewForm(final Model model, final Long solutionId, final String role) {

        model.addAttribute(
                ROLE,
                role
        );

        solutionService.takeForReview(solutionId);

        return "redirect:/" + role + "/solution/view/" + solutionId;
    }

    /**
     * View for verifying solution.
     *
     * @param solutionId
     * @param taskNum
     * @param role
     * @return view
     */
    public ModelAndView verifyForm(final Long solutionId,
                                   final Integer taskNum,
                                   final String role
    ) {

        ModelAndView model = new ModelAndView("solution/verify");

        model.addObject(
                ROLE,
                role
        );

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
     * @param role
     * @return redirect url
     */
    public String verifyFormProcessing(
            final SolutionVerifyingForm form,
            final Long solutionId,
            final Integer taskNum,
            final BindingResult bindingResult,
            final Model model,
            final String role
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    FORM_ERROR_ATTR,
                    bindingResult.getAllErrors()
            );

            return verifyForm(solutionId, taskNum, role).getViewName();
        }

        model.addAttribute(
                ROLE,
                role
        );

        SolutionInfo solution = solutionService.verify(SolutionVerifyForm.builder()
                .solutionId(solutionId)
                .score(form.getScore())
                .comment(form.getComment())
                .build());

        return "redirect:/" + role + "/solution/view/" + solution.getId();
    }


    @AllArgsConstructor
    @Getter
    public enum SearchFields {

        /**
         * Searching by id.
         */
        TASK_NUM("id", "Id"),

        /**
         * Searching by creating date.
         */
        CREATED_AT("creationTime", "дата создания"),

        /**
         * Searching by last action.
         */
        LAST_ACTION_AT("lastActionTime", "последнее обновление");

        /**
         * Searching by status.
         */
        private final String field;

        /**
         * Field name.
         */
        private final String msg;

        /**
         * Searching field by string from request.
         *
         * @param searchBy
         * @return enum
         */
        public static SearchFields byString(final String searchBy) {
            Optional<SearchFields> searchFields = Arrays
                    .stream(values())
                    .filter(e -> e.name().equals(searchBy))
                    .findFirst();

            return searchFields.orElse(CREATED_AT);
        }
    }
}
