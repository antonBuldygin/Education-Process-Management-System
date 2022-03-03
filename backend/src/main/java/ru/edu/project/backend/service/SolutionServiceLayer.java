package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.api.solutions.SolutionForm;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionReviewForm;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.da.SolutionDALayer;
import ru.edu.project.backend.model.SolutionStatus;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Profile("!STUB")
@Qualifier("SolutionServiceLayer")
public class SolutionServiceLayer implements SolutionService {

    /**
     * Data access layer.
     */
    @Autowired
    private SolutionDALayer daLayer;

    /**
     * Task service.
     */
    @Autowired
    private TaskServiceLayer taskService;

    /**
     * Getting student's solutions.
     *
     * @param studentId
     * @return list of solutions
     */
    @Override
    public List<SolutionInfo> getSolutionsByStudent(final long studentId) {
        List<SolutionInfo> solutionsList = daLayer.getSolutionsByStudent(studentId);

        for (SolutionInfo solution : solutionsList) {
            solution.setTask(taskService.getById(solution.getTaskId()));
            solution.setActionHistory(daLayer.getActionsBySolution(solution.getId()));
        }

        return solutionsList;
    }

    /**
     * Getting student's solution by task id.
     *
     * @param studentId
     * @param taskId
     * @return list of solutions
     */
    @Override
    public SolutionInfo getSolutionByStudentAndTask(final long studentId, final long taskId) {
        SolutionInfo solutionInfo = daLayer.getSolutionByStudentAndTask(studentId, taskId);

        solutionInfo.setTask(taskService.getById(solutionInfo.getTaskId()));
        solutionInfo.setActionHistory(daLayer.getActionsBySolution(solutionInfo.getId()));

        return solutionInfo;
    }

    /**
     * Getting solution info.
     *
     * @param solutionId
     * @return info
     */
    @Override
    public SolutionInfo getDetailedInfo(final long solutionId) {
        SolutionInfo solutionInfo = daLayer.getById(solutionId);

        solutionInfo.setTask(taskService.getById(solutionInfo.getTaskId()));

        solutionInfo.setActionHistory(daLayer.getActionsBySolution(solutionId));

        return solutionInfo;
    }

    /**
     * Creating new solution.
     *
     * @param studentId
     * @param taskId
     * @return solutionInfo
     */
    @Override
    public SolutionInfo takeToWork(final long studentId, final long taskId) {
        SolutionInfo solutionInfo = SolutionInfo.builder()
                .studentId(studentId)
                .taskId(taskId)
                .score(new Score(null))
                .creationTime(new Timestamp(new Date().getTime()))
                .lastActionTime(new Timestamp(new Date().getTime()))
                .status(SolutionStatus.TASK_IN_WORK)
                .build();

        daLayer.save(solutionInfo);

        daLayer.doAction(solutionInfo, null);

        solutionInfo.setTask(taskService.getById(taskId));

        solutionInfo.setActionHistory(daLayer.getActionsBySolution(solutionInfo.getId()));

        return solutionInfo;
    }

    /**
     * Uploading text of solution.
     *
     * @param solutionForm
     * @return solutionInfo
     */
    @Override
    public SolutionInfo uploadSolution(final SolutionForm solutionForm) {

        SolutionInfo solutionInfo = daLayer.getById(solutionForm.getSolutionId());

        if (solutionInfo == null || solutionInfo.getStudentId() != solutionForm.getStudentId()) {
            throw new IllegalArgumentException("No such solution!");
        }

        if (!(solutionInfo.getStatus() != SolutionStatus.TASK_IN_WORK
                || solutionInfo.getStatus() != SolutionStatus.UPLOADED)) {
            throw new IllegalArgumentException("Can't uploading for solution not in status \"TASK_IN_WORK\" or \"UPLOADED\"!");
        }

        solutionInfo.setText(solutionForm.getText());
        solutionInfo.setStatus(SolutionStatus.UPLOADED);
        solutionInfo.setLastActionTime(new Timestamp(new Date().getTime()));

        daLayer.save(solutionInfo);

        if (solutionInfo.getStatus() == SolutionStatus.TASK_IN_WORK) {
            daLayer.doAction(solutionInfo, solutionForm.getComment());
        } else {
            daLayer.updateAction(solutionInfo, solutionForm.getComment());
        }
        solutionInfo.setActionHistory(daLayer.getActionsBySolution(solutionInfo.getId()));

        return solutionInfo;
    }

    /**
     * Taking solution for review.
     *
     * @param solutionId
     * @return SolutionInfo
     */
    @Override
    public SolutionInfo takeForReview(final long solutionId) {

        SolutionInfo solutionInfo = daLayer.getById(solutionId);

        if (solutionInfo == null) {
            throw new IllegalArgumentException("No such solution!");
        }

        if (solutionInfo.getStatus() != SolutionStatus.UPLOADED) {
            throw new IllegalArgumentException("Can't taking for review for solution not in status \"UPLOADED\"!");
        }

        solutionInfo.setStatus(SolutionStatus.IN_REVIEW);
        solutionInfo.setLastActionTime(new Timestamp(new Date().getTime()));

        daLayer.save(solutionInfo);

        daLayer.doAction(solutionInfo, null);

        solutionInfo.setActionHistory(daLayer.getActionsBySolution(solutionId));

        return solutionInfo;

    }

    /**
     * Solution is verified.
     *
     * @param solutionReviewForm
     * @return SolutionInfo
     */
    @Override
    public SolutionInfo verify(final SolutionReviewForm solutionReviewForm) {

        SolutionInfo solutionInfo = daLayer.getById(solutionReviewForm.getSolutionId());

        if (solutionInfo == null || solutionInfo.getStudentId() != solutionReviewForm.getStudentId()) {
            throw new IllegalArgumentException("No such solution!");
        }

        if (solutionInfo.getStatus() != SolutionStatus.IN_REVIEW) {
            throw new IllegalArgumentException("Can't verifying for solution not in status \"IN_REVIEW\"!");
        }

        solutionInfo.setScore(new Score(solutionReviewForm.getScore()));
        solutionInfo.setCheckedTime(new Timestamp(new Date().getTime()));
        solutionInfo.setStatus(SolutionStatus.CHECKED);
        solutionInfo.setLastActionTime(new Timestamp(new Date().getTime()));

        daLayer.save(solutionInfo);

        daLayer.doAction(solutionInfo, solutionReviewForm.getComment());

        solutionInfo.setActionHistory(daLayer.getActionsBySolution(solutionInfo.getId()));

        return solutionInfo;
    }

    /**
     * Getting solutions by task id.
     *
     * @param taskId
     * @return list of solutions
     */
    @Override
    public List<SolutionInfo> getSolutionsByTask(final long taskId) {
        return daLayer.getSolutionsByTask(taskId);
    }
}
