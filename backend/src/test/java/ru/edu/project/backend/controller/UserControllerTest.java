package ru.edu.project.backend.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.edu.project.backend.api.user.UserInfo;
import ru.edu.project.backend.da.UserDALayer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static ru.edu.project.backend.controller.UserController.EMPTY_USER;

public class UserControllerTest {

    public static final Long USER_ID = 1L;
    @Mock
    UserDALayer userServiceDA;

    @InjectMocks
    UserController userController;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void register() {
        UserInfo userInfo = mock(UserInfo.class);

        when(userServiceDA.register(userInfo)).thenReturn(userInfo);

        when(userInfo.getId()).thenReturn(USER_ID);

        Long result = userController.register(userInfo);

        assertEquals(result, USER_ID);

        verify(userServiceDA).register(userInfo);
    }

    @Test(expected = IllegalStateException.class)
    public void registerWithError() {
        UserInfo userInfo = mock(UserInfo.class);

        when(userServiceDA.register(userInfo)).thenReturn(null);

        Long result = userController.register(userInfo);

    }

    @Test
    public void loadUserByUsername() {
        String username = "username";

        UserInfo userInfo = mock(UserInfo.class);

        when(userServiceDA.findByUsername(username)).thenReturn(userInfo);

        UserInfo result = userController.loadUserByUsername(username);

        assertEquals(userInfo, result);

        verify(userServiceDA).findByUsername(username);
    }


    @Test
    public void loadUserByUsernameEmptyUser() {
        String username = "username";

        when(userServiceDA.findByUsername(username)).thenReturn(null);

        UserInfo result = userController.loadUserByUsername(username);

        assertEquals(EMPTY_USER, result);

        verify(userServiceDA).findByUsername(username);
    }
}