package ru.edu.project.backend.api.actiongroups;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CreateActionRequest {

    /**
     * Id заявки.
     */
    private long requestId;

    /**
     * Действие.
     */
    private ActionInterface action;

}
