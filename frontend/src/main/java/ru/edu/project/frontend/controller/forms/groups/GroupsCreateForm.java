package ru.edu.project.frontend.controller.forms.groups;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GroupsCreateForm {

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
