package ru.edu.project.backend.da.jpa.converter;

import org.junit.Before;
import org.junit.Test;
import ru.edu.project.backend.api.user.UserInfo;
import ru.edu.project.backend.da.jpa.entity.UserEntity;

import static org.junit.Assert.*;

public class UserInfoMapperTest {

    public static final Long USER_ID = 111L;
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String ROLES = "ROLES";
    public static final boolean ENABLED = true;
    UserInfoMapper userInfoMapper;

    @Before
    public void setUp() throws Exception {
        userInfoMapper = new UserInfoMapperImpl();
    }

    @Test
    public void entityToInfo() {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(USER_ID);
        userEntity.setUsername(USERNAME);
        userEntity.setPassword(PASSWORD);
        userEntity.setRoles(ROLES);
        userEntity.setEnabled(ENABLED);

        UserInfo result = userInfoMapper.map(userEntity);

        assertEquals(USER_ID, result.getId());
        assertEquals(USERNAME, result.getUsername());
        assertEquals(PASSWORD, result.getPassword());
        assertEquals(ROLES, result.getRoles());
        assertEquals(ENABLED, result.getEnabled());

    }

    @Test
    public void entityToInfoNull() {
        UserEntity userEntity = null;

        UserInfo result = userInfoMapper.map(userEntity);

        assertNull(result);
    }

    @Test
    public  void userToEntity() {
        UserInfo userInfo = UserInfo.builder()
                .id(USER_ID)
                .username(USERNAME)
                .password(PASSWORD)
                .roles(ROLES)
                .enabled(ENABLED)
                .build();

        UserEntity result = userInfoMapper.map(userInfo);

        assertEquals(USER_ID, result.getId());
        assertEquals(USERNAME, result.getUsername());
        assertEquals(PASSWORD, result.getPassword());
        assertEquals(ROLES, result.getRoles());
        assertEquals(ENABLED, result.getEnabled());
    }

    @Test
    public void userToEntityNull() {
        UserInfo userInfo = null;

        UserEntity result = userInfoMapper.map(userInfo);

        assertNull(result);
    }
}