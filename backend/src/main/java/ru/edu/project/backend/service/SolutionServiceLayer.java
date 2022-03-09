package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.Score;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionForm;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionVerifyForm;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.api.students.StudentInfo;
import ru.edu.project.backend.api.tasks.TaskInfo;
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
     * Student service.
     */
    @Autowired
    private StudentServiceLayer studentService;


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
            setExtraInfo(solution);
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

        setExtraInfo(solutionInfo);

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

        if (solutionInfo == null) {
            throw new IllegalArgumentException("No such solution!");
        }

        setExtraInfo(solutionInfo);

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

        setExtraInfo(solutionInfo);

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

        setExtraInfo(solutionInfo);

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

        setExtraInfo(solutionInfo);

        return solutionInfo;

    }

    /**
     * Solution is verified.
     *
     * @param solutionVerifyForm
     * @return SolutionInfo
     */
    @Override
    public SolutionInfo verify(final SolutionVerifyForm solutionVerifyForm) {

        SolutionInfo solutionInfo = daLayer.getById(solutionVerifyForm.getSolutionId());

        if (solutionInfo == null) {
            throw new IllegalArgumentException("No such solution!");
        }

        if (solutionInfo.getStatus() != SolutionStatus.IN_REVIEW) {
            throw new IllegalArgumentException("Can't verifying for solution not in status \"IN_REVIEW\"!");
        }

        solutionInfo.setScore(new Score(solutionVerifyForm.getScore()));
        solutionInfo.setCheckedTime(new Timestamp(new Date().getTime()));
        solutionInfo.setStatus(SolutionStatus.CHECKED);
        solutionInfo.setLastActionTime(new Timestamp(new Date().getTime()));

        daLayer.save(solutionInfo);

        daLayer.doAction(solutionInfo, solutionVerifyForm.getComment());

        setExtraInfo(solutionInfo);

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
        List<SolutionInfo> solutionsByTask = daLayer.getSolutionsByTask(taskId);

        for (SolutionInfo solution: solutionsByTask) {
            setExtraInfo(solution);
        }
        return solutionsByTask;
    }

    /**
     * Searching for solutions.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    public PagedView<SolutionInfo> searchSolutions(final SolutionSearch recordSearch) {
        PagedView<SolutionInfo> solutionsList = daLayer.search(recordSearch);

        if (solutionsList != null && solutionsList.getTotal() > 0) {
            for (SolutionInfo solution : solutionsList.getElements()) {
                setExtraInfo(solution);
            }
        }

        return solutionsList;
    }

    private void setExtraInfo(final SolutionInfo solution) {
        setTaskInfo(solution);
        setStudentName(solution);
        solution.setActionHistory(daLayer.getActionsBySolution(solution.getId()));
    }

    private void setStudentName(final SolutionInfo solution) {
        StudentInfo student = studentService.getById(solution.getStudentId());
        solution.setStudentName(student.getFirstName() + " "
                                + student.getLastName()
                                );
    }

    private void setTaskInfo(final SolutionInfo solution) {
        TaskInfo task = taskService.getById(solution.getTaskId());
        solution.setTaskId(task.getId());
        solution.setTaskText(task.getText());
        solution.setTaskNum(task.getNum());
        solution.setTaskTitle(task.getTitle());
    }
}
