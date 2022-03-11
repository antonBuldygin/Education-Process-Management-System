package ru.edu.project.backend.api.solutions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SolutionVerifyForm {
    /** Solution id.
     *
     */
    private Long solutionId;

    /** Score.
     *
     */
    private Integer score;

    /** Comment.
     *
     */
    private String comment;

}
