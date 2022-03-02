package ru.edu.project.backend.api.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = SimpleStatus.class)
public interface Status {

    /**
     * Get status code.
     *
     * @return code
     */
    Long getCode();

    /**
     * Get type message.
     *
     * @return code
     */
    String getMessage();

}
