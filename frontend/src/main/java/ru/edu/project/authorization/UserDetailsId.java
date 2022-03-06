package ru.edu.project.authorization;

import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


@Getter
public class UserDetailsId extends User {

    /**
     * User id.
     */
    private long userId;

    /**
     * Constructor.
     *
     * @param id
     * @param username
     * @param password
     * @param role
     */
    public UserDetailsId(final long id, final String username, final String password, final String role) {
        super(username, password, AuthorityUtils.createAuthorityList(role));
        userId = id;
    }

}
