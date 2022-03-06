package ru.edu.project.backend.api.user;

import ru.edu.project.backend.api.common.AcceptorArgument;

public interface UserService {

    /**
     * User registering.
     *
     * @param userInfo
     * @return id
     */
    @AcceptorArgument
    Long register(UserInfo userInfo);

    /**
     * Getting user's info.
     *
     * @param username
     * @return username
     */
    UserInfo loadUserByUsername(String username);
}
