package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.StudentEntity;



@Repository
public interface StudentEntityRepository extends CrudRepository<StudentEntity, Long> {

}
