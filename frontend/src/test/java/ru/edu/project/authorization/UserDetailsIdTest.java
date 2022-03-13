package ru.edu.project.authorization;

import org.apache.catalina.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDetailsIdTest {

    @Test
    public void getUserId() {
        UserDetailsId userDetailsId = new UserDetailsId(1L, "Username", "Password", "Role");

        assertEquals((Long)1L, userDetailsId.getUserId());

    }
}