package ru.edu.project.backend.api.groups;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.teachers.Teacher;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@Jacksonized
public class GroupInfo {

    /**
     * id заявки.
     */
    private Long id;


    /**
     * Статус.
     */
    private Status status;

    /**
     * Время создания заявки.
     */
    private Timestamp currentDate;

    /**
     * Запланированное время визита.
     */
    private Timestamp createdAt;

    /**
     * Время последнего изменения заявки.
     */
    private Timestamp lastActionAt;

    /**
     * Время закрытия заявки.
     */
    private Timestamp closedAt;


    /**
     * Комментарий клиента.
     */
    private String comment;

    /**
     *  преподаватели.
     *  Список.
     */
    private List<Teacher> teachers;

    /**
     * студенты группы.
     * Список.
     */
    private List<Teacher> students;

    /**
     * История действий.
     */
    private List<Action> actionHistory;



}
