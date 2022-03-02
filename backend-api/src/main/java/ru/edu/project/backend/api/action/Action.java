package ru.edu.project.backend.api.action;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import ru.edu.project.backend.api.common.Status;

import java.sql.Timestamp;

@Getter
@Builder
@Jacksonized
public class Action {

    /**
     * Action time.
     */
    private Timestamp time;

    /**
     * Action status.
     */
    private Status status;

    /**
     * Comment.
     */
    private String comment;

}
