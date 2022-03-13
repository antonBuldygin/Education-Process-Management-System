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
     * Student id.
     */
    private Long id;

    /**
     * Group id.
     */
    private Long groupId;

    /**
     * firstName.
     */
    private String firstName;

    /**
     * lastName.
     */
    private String lastName;

    /**
     * email.
     */
    private String email;

    /**
     * birthday.
     */
    private Timestamp birthday;

    /**
     * phone.
     */
    private String phone;
}
