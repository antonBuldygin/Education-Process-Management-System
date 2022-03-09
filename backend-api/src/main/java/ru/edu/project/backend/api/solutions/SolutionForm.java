package ru.edu.project.backend.api.solutions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolutionForm {

    /** Solution id.
     *
     */
    private Long solutionId;

    /**
     * Student id.
     */
    private Long studentId;

    /** Text of solution.
     *
     */
    private String text;

    /** Comment.
     *
     */
    private String comment;
}
