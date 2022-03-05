package ru.edu.project.frontend.controller.forms.tasks;

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
public class TaskCreateForm {

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
     * Number.
     */
    @NotNull
    private Integer num;

    /**
     * Task title.
     */
    @NotNull
    private String title;

    /**
     * Task text.
     */
    private String text;

    /**
     * Start date of task.
     */
    @NotEmpty
    private String startDate;

    /**
     * End date of task.
     */
    @NotEmpty
    private String endDate;

    /**
     * Getting calendar object for startDate.
     *
     * @return Timestamp
     */
    @SneakyThrows
    public Timestamp getStartDate() {
        Date parsed = FORMAT.parse(startDate);
        return new Timestamp(parsed.getTime());
    }

    /**
     * Getting calendar object for endDate.
     *
     * @return Timestamp
     */
    @SneakyThrows
    public Timestamp getEndDate() {
        Date parsed = FORMAT.parse(endDate);
        return new Timestamp(parsed.getTime());

    }

}
