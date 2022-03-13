package ru.edu.project.frontend.controller.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
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
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.groups.GroupForm;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.groups.UpdateStatusRequest;
import ru.edu.project.backend.api.teachers.TeacherService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    /**
     * Имя атрибута с заявками.
     */
    public static final String REQUESTS_ATTR = "requestsPage";

    /**
     * Имя атрибута со списком полей сортировки.
     */
    public static final String ORDER_FIELDS = "orderFields";

    /**
     * Имя атрибута с текущим полем сортировки.
     */
    public static final String ORDER_BY_FIELD = "orderByField";

    /**
     * Имя атрибута с направлением.
     */
    public static final String IS_ASC = "isAsc";

    /**
     * Имя атрибута заявки.
     */
    public static final String RECORD_ATTR = "record";

    /**
     * Имя атрибута статусов заявки.
     */
    public static final String STATUSES_ATTR = "statuses";

    /**
     * Атрибут модели для хранения списка доступных услуг.
     */
    public static final String JOBS_ATTR = "teachers";

    /**
     * Атрибут модели для хранения списка ошибок.
     */
    public static final String FORM_ERROR_ATTR = "errorsList";

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
     * Просмотр заявок.
     *
     * @param searchBy
     * @param isAsc
     * @param page
     * @param perPage
     * @param searchId
     * @return modelAndView
     */
    @GetMapping("/")
    public ModelAndView index(
            @RequestParam(name = "searchBy", required = false, defaultValue = "") final String searchBy,
            @RequestParam(name = "direct", required = false, defaultValue = "1") final boolean isAsc,
            @RequestParam(name = "page", required = false, defaultValue = "1") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "id", required = false, defaultValue = "-1") final int searchId
    ) {

        if (searchId > 0) {
            return new ModelAndView("redirect:/manager/view/" + searchId);
        }

        ModelAndView model = new ModelAndView("manager/index");
        SearchFields searchByField = SearchFields.byString(searchBy);

        /*
         * На странице ведем отсчет страниц с 1, на бэке с 0
         */
        model.addObject(
                REQUESTS_ATTR,
                groupsService.searchRequests(RecordSearch.by(searchByField.getField(), isAsc, page - 1, perPage))
        );

        model.addObject(ORDER_FIELDS, SearchFields.values());
        model.addObject(ORDER_BY_FIELD, searchByField);
        model.addObject(IS_ASC, isAsc);

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

        ModelAndView modelAndView = new ModelAndView("manager/view");
        modelAndView.addObject(RECORD_ATTR, groupsService.getDetailedInfo(id));
        modelAndView.addObject(STATUSES_ATTR, RequestStatus.values());

        groupsService.getDetailedInfo(id).getStatus().getMessage();
        return modelAndView;
    }


    /**
     * Изменение статуса заявки.
     *
     * @param id
     * @param statusCode
     * @return redirect
     */
    @GetMapping("/view/{id}/setStatus")
    public String updateStatus(@PathVariable("id") final long id, @RequestParam("status") final int statusCode) {

        RequestStatus status = RequestStatus.getByCode(statusCode);

        if (status == null) {
            return "redirect:/manager/view/" + id + "?error=status_invalid";
        }

        boolean res = groupsService.updateStatus(UpdateStatusRequest.builder()
                .groupId(id)
                .status(status)
                .build());


        return "redirect:/manager/view/" + id + "?updatedStatus=" + res;
    }

    @Getter
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum RequestStatus implements Status {
        /**
         * Статус.
         */
        CREATED(1L, "Группа создана"),

        /**
         * Статус.
         */
        NEW_STUDENT(2L, "Добавлен студент"),

        /**
         * Статус.
         */
        NEW_TASK(3L, "Добавлено новое задание");



        /**
         * Код.
         */
        private Long code;

        /**
         * Текст.
         */
        private String message;

        /**
         * Получаем enum по коду.
         *
         * @param code
         * @return enum or null
         */
        public static RequestStatus getByCode(final int code) {
            for (RequestStatus e : values()) {
                if (e.getCode() == code) {
                    return e;
                }
            }
            return null;
        }
    }

    @AllArgsConstructor
    @Getter
    public enum SearchFields {

        /**
         * Поиск по ID.
         */
        ID("id", "id"),

        /**
         * Поиск по дате создания.
         */
        CREATED_AT("createdAt", "дата создания"),

        /**
         * Поиск по последнему обновлению.
         */
        LAST_ACTION_AT("lastActionAt", "последнее обновление");

        /**
         * Поиск по статусу.
         */
        private final String field;

        /**
         * Название поля.
         */
        private final String msg;

        /**
         * Поиск поля по строке из запроса.
         *
         * @param searchBy
         * @return enum
         */
        public static SearchFields byString(final String searchBy) {
            Optional<SearchFields> searchFields = Optional.empty();
            for (SearchFields e : values()) {
                if (e.name().equals(searchBy)) {
                    searchFields = Optional.of(e);
                    break;
                }
            }

            return searchFields.orElse(CREATED_AT);
        }
    }

    /**
     * удаление группы.
     *
     * @param id
     * @return "groups/index"
     */
    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable("id") final long id) {
        groupsService.deleteGroup(id);
        return "redirect:/manager/";
    }


    /**
     * Отображение формы для создания заявки.
     *
     * @param model
     * @return modelAndView
     */
    @GetMapping("/create")
    public String createForm(final Model model) {
        model.addAttribute(JOBS_ATTR, teacherService.getAvailable());
        return "manager/create";
    }


    /**
     * Получаем форму с предварительной валидацией.
     *
     * @param form
     * @param bindingResult результат валидации формы
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

            //после добавления ошибок в модель
            return createForm(model); //отображаем форму
        }

        GroupInfo request = groupsService.createGroup(GroupForm.builder()
                .dateCreatedRf(form.getDateCreated())
                .selectedTeachers(form.getTeachers())
                .comment(form.getComment())
                .build());

        return "redirect:/manager/?created=" + request.getId();
    }

    @Getter
    @Setter
    public static class CreateForm {

        /**
         * Для парсинга даты.
         */
        private static final DateFormat FORMAT;

        static {
            FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        }

        /**
         * Выбранные услуги.
         */
        @NotNull
        private List<Long> teachers;

        /**
         * Выбранное время посещения.
         */
        @NotEmpty
        private String dateCreatedForm;

        /**
         * Описание проблемы.
         */
        @NotNull
        private String comment;

        /**
         * Получение объекта календаря с временем посещения.
         *
         * @return календарь
         */
        @SneakyThrows
        public Timestamp getDateCreated() {
            Date parsed = FORMAT.parse(dateCreatedForm);
            return new Timestamp(parsed.getTime());
        }
    }

}
