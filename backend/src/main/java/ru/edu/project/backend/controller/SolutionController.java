package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.SolutionSearch;
import ru.edu.project.backend.api.solutions.SolutionForm;
import ru.edu.project.backend.api.solutions.SolutionInfo;
import ru.edu.project.backend.api.solutions.SolutionReviewForm;
import ru.edu.project.backend.api.solutions.SolutionService;
import ru.edu.project.backend.service.SolutionServiceLayer;

import java.util.List;

@RestController
@RequestMapping("/solution")
public class SolutionController implements SolutionService {

    /**
     * Delegate for solution service layer.
     */
    @Autowired
    private SolutionServiceLayer delegate;

    /**
     * Getting student's solutions.
     *
     * @param studentId
     * @return list of solutions
     */
    @Override
    @GetMapping("/getSolutionsByStudent/{id}")
    public List<SolutionInfo> getSolutionsByStudent(@PathVariable("id") final long studentId) {
        return delegate.getSolutionsByStudent(studentId);
    }

    /**
     * Getting solutions by task id.
     *
     * @param taskId
     * @return list of solutions
     */
    @Override
    @GetMapping("/getSolutionsByTask/{id}")
    public List<SolutionInfo> getSolutionsByTask(@PathVariable("id") final long taskId) {
        return null;
    }

    /**
     * Searching for solutions.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    @PostMapping("/searchSolutions")
    public PagedView<SolutionInfo> searchSolutions(@RequestBody final SolutionSearch recordSearch) {
        return delegate.searchSolutions(recordSearch);
    }


    /**
     * Getting student's solution by task id.
     *
     * @param studentId
     * @param taskId
     * @return list of solutions
     */
    @Override
    @GetMapping("/getSolutionByStudentAndTask/{studentId}/{taskId}")
    public SolutionInfo getSolutionByStudentAndTask(@PathVariable("studentId") final long studentId, @PathVariable("taskId") final long taskId) {
        return delegate.getSolutionByStudentAndTask(studentId, taskId);
    }

    /**
     * Getting solution info.
     *
     * @param solutionId
     * @return info
     */
    @Override
    @GetMapping("/getDetailedInfo/{id}")
    public SolutionInfo getDetailedInfo(@PathVariable("id") final long solutionId) {
        return delegate.getDetailedInfo(solutionId);
    }

    /**
     * Creating new solution.
     *
     * @param studentId
     * @param taskId
     * @return solutionInfo
     */
    @Override
    @GetMapping("/takeToWork/{studentId}/{taskId}")
    public SolutionInfo takeToWork(@PathVariable("studentId") final long studentId, @PathVariable("taskId") final long taskId) {
        return delegate.takeToWork(studentId, taskId);
    }

    /**
     * Uploading text of solution.
     *
     * @param solutionForm
     * @return solutionInfo
     */
    @Override
    @PostMapping("/uploadSolution")
    public SolutionInfo uploadSolution(@RequestBody final SolutionForm solutionForm) {
        return delegate.uploadSolution(solutionForm);
    }

    /**
     * Taking solution for review.
     *
     * @param solutionId
     * @return SolutionInfo
     */
    @Override
    @GetMapping("/takeForReview/{id}")
    public SolutionInfo takeForReview(@PathVariable("id") final long solutionId) {
        return delegate.takeForReview(solutionId);
    }

    /**
     * Solution is verified.
     *
     * @param solutionReviewForm
     * @return SolutionInfo
     */
    @Override
    @PostMapping("/verify")
    public SolutionInfo verify(@RequestBody final SolutionReviewForm solutionReviewForm) {
        return delegate.verify(solutionReviewForm);
    }


}
