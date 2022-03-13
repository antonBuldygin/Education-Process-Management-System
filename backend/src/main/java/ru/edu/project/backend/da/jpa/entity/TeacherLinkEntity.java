package ru.edu.project.backend.da.jpa.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table (name = "JOBS_LINK",
/*
         * Описание свойств таблицы.
         * Нужно в случае отказа от liquibase и генерации
         * структуры БД через встроенный DDL.
        */
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"job_id", "request_id"})
        }
)
public class TeacherLinkEntity {
    /**
     * Так как у нас составной ключ, необходимо вынести его в отдельную встраиваемую сущность.
     */
    @EmbeddedId
    private JobLinkId pk;

    /**
     * Связь многие к одному с таблицей JOB_TYPE через поле job_id.
     */
    @ManyToOne
    @MapsId("job_id")
    private TeacherEntity job;

    /**
     * Связь многие к одному с таблицей GROUP_G через поле request_id.
     */
    @ManyToOne
    @JoinColumn(name = "request_id", insertable = false, updatable = false, referencedColumnName = "id")
    private GroupsEntity groupg;

    /**
     * Встраиваемая сущность описывающая поля входящие в состав составного ключа таблицы.
     */
    @Embeddable
    @Getter
    @Setter
    public static class JobLinkId implements Serializable {

        /**
         * Составная часть ключа request_id.
         */
        @Column(name = "request_id")
        private Long requestId;

        /**
         * Составная часть ключа job_id.
         */
        @Column(name = "job_id")
        private Long teacherId;

    }

    /**
     * Билдер первичного ключа.
     *
     * @param requestId
     * @param teacherId
     * @return pk
     */
    public static JobLinkId pk(final long requestId, final long teacherId) {
        JobLinkId id = new JobLinkId();
        id.setRequestId(requestId);
        id.setTeacherId(teacherId);
        return id;
    }

}
