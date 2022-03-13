package ru.edu.project.backend.da;


import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionInfo;

import java.util.List;

public interface SolutionDALayer {


    /** Getting solutions by student id.
     *
     * @param studentId
     * @return SolutionInfo
     */
    List<SolutionInfo> getSolutionsByStudent(long studentId);

    /**
     * Searching for solutions .
     *
     * @param recordSearch
     * @return list
     */
    PagedView<SolutionInfo> search(SolutionSearch recordSearch);

    /**
     * Getting student's solution by task id.
     *
     * @param studentId
     * @param taskId
     * @return list of solutions
     */
    SolutionInfo getSolutionByStudentAndTask(long studentId, long taskId);

    /**
     * Getting solutions by task id.
     *
     * @param taskId
     * @return list of solutions
     */
    List<SolutionInfo> getSolutionsByTask(long taskId);

    /** Getting solution by id.
     *
     * @param id
     * @return SolutionInfo
     */
    SolutionInfo getById(long id);

    /** Inserting/updating solution.
     *
     * @param solutionInfo
     * @return SolutionInfo
     */
    SolutionInfo save(SolutionInfo solutionInfo);

    /** Create solution action link.
     *
     * @param solutionInfo
     * @param comment
     */
    void doAction(SolutionInfo solutionInfo, String comment);

    /** Getting list of actions by solution id.
     *
     * @param solutionId
     * @return List<ActionInterface>
     */
    List<Action> getActionsBySolution(long solutionId);

    /** Update solution action link.
     *
     * @param solutionInfo
     * @param comment
     */
    void updateAction(SolutionInfo solutionInfo, String comment);

    /**
     * Getting all solutions.
     *
     * @return list of solutions
     */
    List<SolutionInfo> getAllSolutions();
}
