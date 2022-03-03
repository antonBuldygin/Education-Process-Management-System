package ru.edu.project.backend.api.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@AllArgsConstructor
@JsonDeserialize
@Jacksonized
public class Score {

    /**
     * Score value in 100-point system.
     */
    private Integer value;

    /**
     * Constructor.
     */
    public Score() {
    }
}
