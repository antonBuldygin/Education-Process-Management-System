package ru.edu.project.backend.api.actiongroups;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;

@Getter
@Builder
@Jacksonized
public class SimpleAction implements ActionInterface {

    /**
     * Время действия.
     */
    private Timestamp time;

    /**
     * Код действия.
     */
    private Long typeCode;

    /**
     * Название действия.
     */
    private String typeMessage;


    /**
     * Сообщение.
     */
    private String message;

}
