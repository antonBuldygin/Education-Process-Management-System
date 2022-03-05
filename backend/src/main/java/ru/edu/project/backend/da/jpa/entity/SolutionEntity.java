package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.solutions.SolutionInfoAbstract;
import ru.edu.project.backend.da.jpa.converter.ScoreConverter;
import ru.edu.project.backend.da.jpa.converter.SolutionStatusConverter;

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
@Getter
@Setter
@Table(name = "solution")
public class SolutionEntity implements SolutionInfoAbstract {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "solution_seq")
    @SequenceGenerator(name = "solution_seq", sequenceName = "solution_id_sequence", allocationSize = 1)
    private Long id;

    /**
     * task_id.
     */
    private Long taskId;

    /**
     * student_id.
     */
    private Long studentId;

    /**
     * text.
     */
    private String text;

    /**
     * status.
     */
    @Convert(converter = SolutionStatusConverter.class)
    private Status status;

    /**
     * score.
     */
    @Convert(converter = ScoreConverter.class)
    private Score score;

    /**
     * createdAt.
     */
    @Column(name = "creation_time")
    private Timestamp creationTime;

    /**
     * lastActionAt.
     */
    @Column(name = "last_action_time")
    private Timestamp lastActionTime;

    /**
     * checkedAt.
     */
    @Column(name = "checked_time")
    private Timestamp checkedTime;

    /**
     * action list.
     *
     * @return list
     */
    @Override
    public List<Action> getActionHistory() {
        return null;
    }

}
