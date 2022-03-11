package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import ru.edu.project.backend.api.tasks.TaskAbstract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "task")
public class TaskEntity implements TaskAbstract {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_id_sequence", allocationSize = 1)
    private Long id;

    /**
     * group_id.
     */
    @Column(name = "group_id")
    private Long groupId;

    /**
     * num.
     */
    @Column(name = "num")
    private Integer num;

    /**
     * title.
     */
    @Column(name = "title")
    private String title;

    /**
     * text.
     */
    @Column(name = "text")
    private String text;

    /**
     * startDate.
     */
    @Column(name = "start_date")
    private Timestamp startDate;

    /**
     * endDate.
     */
    @Column(name = "end_date")
    private Timestamp endDate;
}
