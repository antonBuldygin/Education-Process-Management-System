package ru.edu.project.backend.api.students;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;

@Getter
@Builder
@Setter
@Jacksonized
public class StudentInfo {

    /**
     * definition.
     */
    private Long id;

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
    private Long groupId;

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
