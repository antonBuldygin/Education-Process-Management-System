package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.TaskEntity;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface TaskEntityRepository extends CrudRepository<TaskEntity, Long> {

    /**
     * Getting available tasks on current date.
     *
     * @param groupId
     * @param date
     * @return List<TaskEntity>
     */
    @Query("select t from TaskEntity t where t.startDate <= :date and t.endDate >= :date and t.groupId = :groupId")
    List<TaskEntity> getAvailable(@Param("date") Timestamp date, @Param("groupId") long groupId);


    /**
     * Getting all tasks by group id.
     *
     * @param groupId
     * @return list of tasks
     */
    List<TaskEntity> findByGroupId(long groupId);
}
