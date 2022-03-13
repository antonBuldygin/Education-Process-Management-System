package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.GroupsEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface GroupsEntityRepository extends PagingAndSortingRepository<GroupsEntity, Long>, JpaSpecificationExecutor<GroupsEntity> {

    /**
     * Поиск записей по полю id.
     *
     * @return list.
     */
    List<GroupsEntity> findAll();

    /**
     * Поиск записей по полю GroupsEntity.
     * @param id
     * @return list.
     */
    Optional<GroupsEntity> findById(long id);

}
