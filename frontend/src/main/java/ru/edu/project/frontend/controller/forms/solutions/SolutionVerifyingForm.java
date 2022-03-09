package ru.edu.project.frontend.controller.forms.solutions;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SolutionVerifyingForm {
    /**
     * Max score value.
     */
    private static final int MAX_SCORE = 100;

    /**
     * Min score value.
     */
    private static final int MIN_SCORE = 0;

    /**
     * Score.
     */
    @NotNull
    @Max(MAX_SCORE)
    @Min(MIN_SCORE)
    private Integer score;

    /**
     * Comment.
     */
    private String comment;

}
