package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.SolutionEntity;

import java.util.List;


@Repository
public interface SolutionEntityRepository extends PagingAndSortingRepository<SolutionEntity, Long>, JpaSpecificationExecutor<SolutionEntity> {

    /**
     * Finding by studentId.
     *
     * @param studentId
     * @return list of entity
     */
    List<SolutionEntity> findByStudentId(Long studentId);


    /**
     * Finding by studentId.
     *
     * @param pageable
     * @param studentId
     * @return list of entity
     */
    Page<SolutionEntity> findByStudentId(Long studentId, Pageable pageable);

    /**
     * Finding by studentId and taskId.
     *
     * @param studentId
     * @param taskId
     * @return SolutionEntity
     */
    SolutionEntity findByStudentIdAndTaskId(long studentId, long taskId);

    /**
     * Finding by taskId.
     *
     * @param taskId
     * @return list of SolutionEntity
     */
    List<SolutionEntity> findByTaskId(long taskId);
}
