package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.UserEntity;

@Repository
public interface UserEntityRepostitory extends CrudRepository<UserEntity, Long> {

    /**
     * Find users by username.
     *
     * @param username
     * @return userEntity
     */
    UserEntity findByUsername(String username);
}
