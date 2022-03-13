package ru.edu.project.authorization;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.edu.project.app.ServletInitializer;
import ru.edu.project.backend.api.user.UserInfo;
import ru.edu.project.backend.api.user.UserService;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class FrontendUserServiceTest {

    public static final Long USER_ID = 1L;
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String ROLES = "Role";
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserService userService;

    @InjectMocks
    private FrontendUserService frontendUserService;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }


    @Test
    public void insertRow() {

        when(userService.register(any(UserInfo.class))).thenReturn(USER_ID);

        Long result = frontendUserService.insertRow(USERNAME, PASSWORD, ROLES);

        assertEquals(USER_ID, result);

        verify(userService).register(any(UserInfo.class));
    }

    @Test
    public void loadUserByUsername() {

        UserInfo userInfo = UserInfo.builder()
                .id(USER_ID)
                .username(USERNAME)
                .password(PASSWORD)
                .roles(ROLES)
                .build();

        when(userService.loadUserByUsername(any(String.class))).thenReturn(userInfo);

        UserDetailsId result = (UserDetailsId)frontendUserService.loadUserByUsername("Username");

        assertNotNull(result);

        assertEquals(USER_ID, result.getUserId());
        assertEquals(USERNAME, result.getUsername());
        assertEquals(PASSWORD, result.getPassword());

    }
}