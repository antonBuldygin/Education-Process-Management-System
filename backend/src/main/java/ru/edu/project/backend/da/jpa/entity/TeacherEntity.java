package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import ru.edu.project.backend.api.teachers.TeacherAbstract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
//import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "JOB_TYPE")
public class TeacherEntity implements TeacherAbstract {
    /**
     * Первичный ключ.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "job_seq")
    @SequenceGenerator(name = "job_seq", sequenceName = "jobs_id_sequence", allocationSize = 1)
    private Long id;

    /**
     * Название.
     */
    @Column(name = "title")
    private String teacherName;

    /**
     * Описание.
     */
    @Column(name = "desc")
    private String course;

    /**
     * Признак активности.
     */
    @Column(name = "enabled")
    private Boolean enabled;


//    /**
//     * привязка один ко многим.
//     * таблицы job_table и
//     * job_link
//     */
//    @OneToMany
//    @JoinColumn(name = "job_id")
//    private List<TeacherLinkEntity> links;
}

