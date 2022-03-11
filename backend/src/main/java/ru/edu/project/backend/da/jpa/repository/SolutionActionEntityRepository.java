package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.SolutionActionEntity;

import java.util.List;


@Repository
public interface SolutionActionEntityRepository extends CrudRepository<SolutionActionEntity, SolutionActionEntity.SolutionActionLinkId> {

    /**
     * Finding by solutionId.
     *
     * @param solutionId
     * @return SolutionActionEntity
     */
    List<SolutionActionEntity> findAllByPkSolutionId(long solutionId);

}
