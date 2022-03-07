package ru.edu.project.backend.api.students;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;


@Getter
@Builder
@Jacksonized
public class StudentForm {

    /**
     * Task id.
     */
    private Long id;

    /**
     * Group id.
     */
    private Long groupId;

    /**
     * definition.
     */
    private String firstName;

    /**
     * definition.
     */
    private String lastName;

    /**
     * definition.
     */
    private String email;

    /**
     * definition.
     */
    private Timestamp birthday;

    /**
     * definition.
     */
    private String phone;
}
