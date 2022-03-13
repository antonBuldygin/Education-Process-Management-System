package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.Column;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(
        name = "solution_action",

        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"solution_id", "action_type_id"})
        }
)
public class SolutionActionEntity {

    /**
     * Embedded primary key.
     */
    @EmbeddedId
    private SolutionActionLinkId pk;


    /**
     * Action time.
     */
    private Timestamp actionTime;


    /**
     * Comment.
     */
    private String comment;


    @Embeddable
    @Getter
    @Setter
    public static class SolutionActionLinkId implements Serializable {

        /**
         * SolutionId.
         */
        @Column(name = "solution_id")
        private Long solutionId;

        /**
         * ActionTypeId.
         */
        @Column(name = "action_type_id")
        private Long actionTypeId;

    }

    /**
     * Builder for primary key.
     *
     * @param solutionId
     * @param actionTypeId
     * @return pk
     */
    public static SolutionActionLinkId pk(final long solutionId, final long actionTypeId) {
        SolutionActionLinkId id = new SolutionActionLinkId();
        id.setSolutionId(solutionId);
        id.setActionTypeId(actionTypeId);
        return id;
    }
}
