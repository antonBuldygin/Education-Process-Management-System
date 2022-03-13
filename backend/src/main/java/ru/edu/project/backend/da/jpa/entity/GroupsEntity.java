package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import ru.edu.project.backend.api.actiongroups.ActionInterface;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.groups.GroupInfoAbstract;
import ru.edu.project.backend.api.teachers.Teacher;
import ru.edu.project.backend.da.jpa.converter.RequestStatusConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "GROUP_G")
public class GroupsEntity implements GroupInfoAbstract {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "request_seq")
    @SequenceGenerator(name = "request_seq", sequenceName = "request_id_sequence", allocationSize = 1)
    private Long id;


    /**
     * status.
     */
    @Convert(converter = RequestStatusConverter.class)
    private Status status;

//    /**
//     * createdAt.
//     *
//          */
//    @Column(name = "last_action_time")
//    private Timestamp currentDate;

    /**
     * дата создания группы.
     */
    @Column(name = "created_time")
    private Timestamp createdAt;

    /**
     * lastActionAt.
     *
     */
    @Column(name = "last_action_time")
    private Timestamp lastActionAt;


    /**
     * closedAt.
     *

     */
    @Column(name = "closed_time")
    private Timestamp closedAt;

    /**
     * comment.
     *
          */
    @Column(name = "comment")
    private String comment;


    /**
     * преподаватели.
     * Список.
     *
     * @return list
     */
    @Override
    public List<Teacher> getTeachers() {
        return null;
    }

    /**
     * Студенты.
     * Список.
     *
     * @return list
     */
    @Override
    public List<Teacher> getStudents() {
        return null;
    }

    /**
     * action list.
     *
     * @return list
     */
    @Override
    public List<ActionInterface> getActionHistory() {
        return null;
    }
}
