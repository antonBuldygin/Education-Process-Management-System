package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import ru.edu.project.backend.api.students.StudentAbstract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "student")
public class StudentEntity implements StudentAbstract {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "student_seq")
    @SequenceGenerator(name = "student_seq", sequenceName = "student_id_sequence", allocationSize = 1)
    private Long id;

    /**
     * group_id.
     */
    @Column(name = "group_id")
    private Long groupId;

    /**
     * firstName.
     */
    @Column(name = "first_name")
    private String firstName;


    /**
     * lastName.
     */
    @Column(name = "last_name")
    private String lastName;


    /**
     * email.
     */
    @Column(name = "email")
    private String email;

    /**
     * birthday.
     */
    @Column(name = "birthday")
    private Timestamp birthday;

    /**
     * phone.
     */
    @Column(name = "phone")
    private String phone;

}
