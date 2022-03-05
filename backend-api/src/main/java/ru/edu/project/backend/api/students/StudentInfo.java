package ru.edu.project.backend.api.students;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;


@Getter
@Builder
@Setter
@Jacksonized
public class StudentInfo {

    /**
     * Task id.
     */
    private Long id;

    /**
     * Group id.
     */
    private Long groupId;


    /**
     * First name.
     */
    private String firstName;

    /**
     * Second name.
     */
    private String secondName;

    /**
     * Middle name.
     */
    private String middleName;

}
