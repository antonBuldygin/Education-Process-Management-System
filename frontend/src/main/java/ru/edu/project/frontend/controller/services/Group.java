package ru.edu.project.frontend.controller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.servlet.ModelAndView;
import ru.edu.project.backend.api.groups.GroupInfo;
import ru.edu.project.backend.api.groups.GroupsService;
import ru.edu.project.backend.api.groups.GroupForm;
import ru.edu.project.backend.api.teachers.TeacherService;
import ru.edu.project.frontend.controller.forms.groups.GroupsCreateForm;



@Component
public class Group {

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
    public ModelAndView view(final long id) {

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
    public String deleteForm(final long id) {
       groupsService.deleteGroup(id);
        return "redirect:/groups/";
    }


    /**
     * Отображение формы для создания заявки.
     *
     * @param model
     * @return modelAndView
     */
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
    public String createFormProcessing(
            final GroupsCreateForm form,
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


}
