package ru.edu.project.frontend.controller.forms.students;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class StudentCreateForm {

    /**
     * Format for date parsing.
     */
    private static final DateFormat FORMAT;

    static {
        FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * GroupId.
     */
    private Long groupId;

    /**
     * firstName.
     */
    @NotNull
    private String firstName;

    /**
     * lastName.
     */
    @NotNull
    private String lastName;

    /**
     * email.
     */
    private String email;

    /**
     * birthday.
     */
    @NotEmpty
    private String birthday;

    /**
     * phone.
     */
    @NotEmpty
    private String phone;

    /**
     * Getting calendar object for birthday.
     *
     * @return Timestamp
     */
    @SneakyThrows
    public Timestamp getBirthday() {
        Date parsed = FORMAT.parse(birthday);
        return new Timestamp(parsed.getTime());
    }

}
