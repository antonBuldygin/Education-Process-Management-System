package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.TeacherEntity;

import java.util.List;

@Repository
public interface TeacherEntityRepository extends CrudRepository<TeacherEntity, Long> {
    /**
     * Поиск записей по полю enabled.
     * @param enabled
     * @return list entity
     */
    List<TeacherEntity> findAllByEnabled(boolean enabled);
}
