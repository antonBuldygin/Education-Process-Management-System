package ru.edu.project.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.edu.project.backend.api.common.Status;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SolutionStatus implements Status {

    /**
     * Task has been taken into work.
     */
    TASK_IN_WORK(1L, "Задание получено"),

    /**
     * Solution has been uploaded.
     */
    UPLOADED(2L, "Решение загружено"),

    /**
     * Solution has been submitted for review.
     */
    IN_REVIEW(3L, "Решение поступило на проверку"),

    /**
     * Solution is checked.
     */
    CHECKED(4L, "Решение проверено");

    /**
     * Code.
     */
    private final Long code;

    /**
     * Message.
     */
    private final String message;

    /**
     * Getting enum status by it's code.
     *
     * @param status
     * @return enum or null
     */
    public static Status byCode(final long status) {
        return Arrays.stream(values())
                .filter(s -> s.getCode() == status)
                .findFirst()
                .orElse(null);
    }
}
