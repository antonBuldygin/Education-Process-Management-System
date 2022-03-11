package ru.edu.project.frontend.controller.forms.solutions;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SolutionUploadForm {

    /**
     * Text.
     */
    @NotNull
    private String text;

    /**
     * Comment.
     */
    private String comment;

}
