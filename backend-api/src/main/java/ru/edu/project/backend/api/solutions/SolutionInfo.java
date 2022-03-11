package ru.edu.project.backend.api.solutions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.api.common.Status;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@Jacksonized
public class SolutionInfo implements SolutionInfoAbstract {

    /**
     * Solution id.
     */
    private Long id;

    /**
     * Student id.
     */
    private Long studentId;

    /**
     * Student's name.
     */
    private String studentName;

    /**
     * Task id.
     */
    private Long taskId;

    /**
     * Task num.
     */
    private Integer taskNum;

    /**
     * Task text.
     */
    private String taskText;

    /**
     * Task title.
     */
    private String taskTitle;

    /** Text of solution.
     *
     */
    private String text;

    /**
     * Score in 100-point system.
     */
    private Score score;

    /**
     * Status.
     */
    private Status status;

    /**
     * Time of creation.
     */
    private Timestamp creationTime;

    /**
     * Time of last action on solution.
     */
    private Timestamp lastActionTime;

    /**
     * Time when solution was checked.
     */
    private Timestamp checkedTime;

    /**
     * Action history.
     */
    private List<Action> actionHistory;

}
