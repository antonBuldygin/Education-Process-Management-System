package ru.edu.project.frontend.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.groups.GroupForm;
import ru.edu.project.backend.api.teachers.TeacherService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Для отладки через spring-boot:run не забываем добавить флаг.
 * -Dspring-boot.run.fork=false
 */
@RequestMapping("/groups")
@Controller
public class GroupController {

    /**
     * Атрибут модели для хранения списка ошибок.
     */
    public static final String FORM_ERROR_ATTR = "errorsList";

    /**
     * Атрибут модели для хранения списка доступных услуг.
     */
    public static final String JOBS_ATTR = "teachers";

    /**
     * Атрибут модели для хранения списка заявок.
     */
    public static final String REQUESTS_ATTR = "requests";

    /**
     * Ссылка на сервис заявок.
     */
    @Autowired
    private GroupsService groupsService;

    /**
     * Ссылка на сервис услуг.
     */
    @Autowired
    private TeacherService teacherService;

    /**
     * Отображение заявок клиента.
     *
     * @param model модель для представления
     * @return view
     */
    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute(
                REQUESTS_ATTR,
                groupsService.getAllGroupsInfo()
        );

        return "groups/index";
    }

    /**
     * Просмотр заявки по id.
     *
     * @param id
     * @return modelAndView
     */
    @GetMapping("/view/{id}")
    public ModelAndView view(final @PathVariable("id") long id) {

        ModelAndView model = new ModelAndView("groups/view");

        GroupInfo detailedInfo = groupsService.getDetailedInfo(id);
        if (detailedInfo == null) {
            model.setStatus(HttpStatus.NOT_FOUND);
            model.setViewName("groups/viewNotFound");
            return model;
        }

        model.addObject(
                "record",
                detailedInfo
        );

        return model;
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
        return "redirect:/groups/";
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
        return "groups/create";
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

        return "redirect:/groups/?created=" + request.getId();
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
