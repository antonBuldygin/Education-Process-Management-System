package ru.edu.project.frontend.controller.forms.solutions;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SolutionCreateForm {

        /**
         * Task.
         */
        @NotNull
        private Long task;

}
