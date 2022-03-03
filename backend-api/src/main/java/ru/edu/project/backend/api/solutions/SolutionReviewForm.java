package ru.edu.project.backend.api.solutions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolutionReviewForm {
    /** Solution id.
     *
     */
    private long solutionId;

    /**
     * Student id.
     */
    private Long studentId;

    /** Score.
     *
     */
    private Integer score;

    /** Comment.
     *
     */
    private String comment;

}
