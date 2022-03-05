package ru.edu.project.backend.api.solutions;

public interface SolutionInfoAbstract {

    /**
     * Solution id.
     *
     * @return long
     */
    Long getId();

    /**
     * Student id.
     *
     * @return long
     */
    Long getStudentId();

    /**
     * Task id.
     *
     * @return long
     */
    Long getTaskId();

    /**
     * Text of solution.
     *
     * @return string
     */
    String getText();

    /**
     * Score in 100-point system.
     *
     * @return score
     */
    ru.edu.project.backend.api.common.Score getScore();

    /**
     * Status.
     *
     * @return status
     */
    ru.edu.project.backend.api.common.Status getStatus();

    /**
     * Time of creation.
     *
     * @return timestamp
     */
    java.sql.Timestamp getCreationTime();

    /**
     * Time of last action on solution.
     *
     * @return timestamp
     */
    java.sql.Timestamp getLastActionTime();

    /**
     * Time when solution was checked.
     *
     * @return timestamp
     */
    java.sql.Timestamp getCheckedTime();

    /**
     * Action history.
     *
     * @return action
     */
    java.util.List<ru.edu.project.backend.api.action.Action> getActionHistory();

}
