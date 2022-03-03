package ru.edu.project.backend.api.tasks;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;


@Getter
@Builder
@Jacksonized
public class TaskForm {

    /**
     * Task id.
     */
    private Long id;

    /**
     * Group id.
     */
    private Long groupId;

    /** Task number.
     *
     */
    private Integer num;

    /**
     * Task title.
     */
    private String title;

    /**
     * Task text.
     */
    private String text;

    /**
     * Start date of task.
     */
    private Timestamp startDate;

    /**
     * End date of task.
     */
    private Timestamp endDate;
}
