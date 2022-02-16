package ru.edu.project.backend.api.groups;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@Jacksonized
public class GroupForm {


    /**
     * Желаемое время визита.
     */
    private Timestamp dateCreatedRf;

    /**
     * Выбранные услуги.
     */
    private List<Long> selectedTeachers;

    /**
     * Комментарий.
     */
    private String comment;

//    /**
//     * дата удаления группы.
//     */
//    private Timestamp deletedDate;
}
