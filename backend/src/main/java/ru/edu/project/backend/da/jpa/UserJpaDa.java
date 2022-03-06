package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.user.UserInfo;
import ru.edu.project.backend.da.UserDALayer;
import ru.edu.project.backend.da.jpa.converter.UserInfoMapper;
import ru.edu.project.backend.da.jpa.entity.UserEntity;
import ru.edu.project.backend.da.jpa.repository.UserEntityRepostitory;

@Profile("SPRING_DATA")
@Service
public class UserJpaDa implements UserDALayer {

    /**
     * Repository for users.
     */
    @Autowired
    private UserEntityRepostitory repo;

    /**
     * Mapper.
     */
    @Autowired
    private UserInfoMapper mapper;

    /**
     * User registration.
     *
     * @param info
     * @return userinfo
     */
    @Override
    public UserInfo register(final UserInfo info) {
        UserEntity draft = mapper.map(info);
        draft.setEnabled(true);

        return mapper.map(repo.save(draft));
    }

    /**
     * Find user by username.
     *
     * @param username
     * @return userinfo
     */
    @Override
    public UserInfo findByUsername(final String username) {
        return mapper.map(repo.findByUsername(username));
    }
}
