package ru.edu.project.backend.api.solutions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.tasks.TaskInfo;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@Jacksonized
public class SolutionInfo {

    /**
     * Solution id.
     */
    private Long id;

    /**
     * Student id.
     */
    private Long studentId;

    /**
     * Task id.
     */
    private Long taskId;

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
     * Task.
     */
    private TaskInfo task;

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
