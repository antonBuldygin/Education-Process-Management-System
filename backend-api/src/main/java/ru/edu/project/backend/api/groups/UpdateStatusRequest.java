package ru.edu.project.backend.api.groups;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import ru.edu.project.backend.api.common.Status;

@Getter
@Builder
@Jacksonized
public class UpdateStatusRequest {

    /**
     * Идентификатор заявки.
     */
    private long groupId;

    /**
     * Статус в который нужно перевести заявку.
     */
    private Status status;
}
