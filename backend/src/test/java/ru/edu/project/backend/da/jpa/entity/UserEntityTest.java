package ru.edu.project.backend.da.jpa.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class UserEntityTest {

    public static final Long USER_ID = 111L;
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String ROLES = "Roles";
    public static final Boolean ENABLED = true;


    @Test
    public void entityTest() {
        UserEntity entity = new UserEntity();
        entity.setId(USER_ID);
        entity.setUsername(USERNAME);
        entity.setPassword(PASSWORD);
        entity.setRoles(ROLES);
        entity.setEnabled(ENABLED);

        assertEquals(USER_ID, entity.getId());
        assertEquals(USERNAME, entity.getUsername());
        assertEquals(PASSWORD, entity.getPassword());
        assertEquals(ROLES, entity.getRoles());
        assertEquals(ENABLED, entity.getEnabled());

    }

}