package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.TeacherLinkEntity;

import java.util.List;


@Repository
public interface TeacherLinkEntityRepository extends CrudRepository<TeacherLinkEntity, TeacherLinkEntity.JobLinkId> {
    /**
     * Поиск записей по полю составного ключа pk.RequestId.
     *
     * @param groupId
     * @return list entity
     */
    List<TeacherLinkEntity> findAllByPkRequestId(long groupId);
    /**
     * Поиск записей по полю составного ключа pk.JobId.
     *
     * @param teacherId
     * @return list entity
     */
    List<TeacherLinkEntity> findAllByPkTeacherId(long teacherId);


}
