package ru.edu.project.backend.api.groups;

//import ru.edu.project.backend.api.actiongroups.ActionInterface;
//import ru.edu.project.backend.api.common.Status;
//import ru.edu.project.backend.api.teachers.Teacher;
//
//import java.sql.Timestamp;
//import java.util.List;

import ru.edu.project.backend.api.actiongroups.ActionInterface;

public interface GroupInfoAbstract {

    /**
     * id заявки.
     * @return id
     */
    Long getId();


    /**
     * status.
     *
     * @return status
     */
    ru.edu.project.backend.api.common.Status getStatus();

//    /**
//     * createdAt.
//     *
//     * @return timestamp
//     */
//    java.sql.Timestamp getCurrentDate();

    /**
     * дата создания группы.
     * @return timestamp
     */

    java.sql.Timestamp getCreatedAt();

    /**
     * lastActionAt.
     *
     * @return timestamp
     */
    java.sql.Timestamp getLastActionAt();

    /**
     * closedAt.
     *
     * @return timestamp
     */
    java.sql.Timestamp getClosedAt();


    /**
     * comment.
     *
     * @return comment
     */
    String getComment();

    /**
     *  преподаватели.
     *  Список.
     *  @return list
     */

    java.util.List<ru.edu.project.backend.api.teachers.Teacher> getTeachers();

    /**
     *  Студенты.
     *  Список.
     *  @return list
     */

    java.util.List<ru.edu.project.backend.api.teachers.Teacher> getStudents();

    /**
     * action list.
     *
     * @return list
     */
    java.util.List<ActionInterface> getActionHistory();

}
