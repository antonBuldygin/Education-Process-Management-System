package ru.edu.project.app;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.edu.project.authorization.FrontendUserService;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class IndexControllerTest {

    /**
     * Student's role.
     */
    public static final String ROLE_STUDENT_STR = "ROLE_STUDENT";

    /**
     * Admin's role.
     */
    public static final String ROLE_ADMIN_STR = "ROLE_ADMIN";

    /**
     * Teacher's role.
     */
    public static final String ROLE_TEACHER_STR = "ROLE_TEACHER";

    /**
     * Student's role object.
     */
    public static final SimpleGrantedAuthority ROLE_STUDENT = new SimpleGrantedAuthority(ROLE_STUDENT_STR);

    /**
     * Teacher's role object.
     */
    public static final SimpleGrantedAuthority ROLE_TEACHER = new SimpleGrantedAuthority(ROLE_TEACHER_STR);

    /**
     * Admin's role object.
     */
    public static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority(ROLE_ADMIN_STR);
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String PASSWORD_2 = "Password2";


    @Mock
    FrontendUserService frontendUserService;

    @InjectMocks
    IndexController indexController;

    @Mock
    Authentication authentication;

    @Mock
    FrontendUserService userServiceDa;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    public void indexStudent() {

        Collection authorities = mock(Collection.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenReturn(authorities);

        when(authorities.contains(ROLE_STUDENT)).thenReturn(true);

        String result = indexController.index(authentication);

        assertEquals("redirect:/student/", result);
    }

    @Test
    public void indexTeacher() {

        Collection authorities = mock(Collection.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenReturn(authorities);

        when(authorities.contains(ROLE_TEACHER)).thenReturn(true);

        String result = indexController.index(authentication);

        assertEquals("redirect:/teacher/", result);
    }

    @Test
    public void indexAdmin() {

        Collection authorities = mock(Collection.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenReturn(authorities);

        when(authorities.contains(ROLE_ADMIN)).thenReturn(true);

        String result = indexController.index(authentication);

        assertEquals("redirect:/admin/", result);
    }

    @Test
    public void index() {
        Authentication authentication = null;

        String result = indexController.index(authentication);

        assertEquals("index", result);
    }

    @Test
    public void register() {
        String result = indexController.register();

        assertEquals("register", result);
    }

    @Test
    public void registerProcessStudent() {
        String result = indexController.registerProcess(USERNAME, PASSWORD, PASSWORD, "student");

        assertEquals("redirect:/login", result);

        verify(userServiceDa).insertRow(USERNAME, PASSWORD, ROLE_STUDENT_STR);
    }

    @Test
    public void registerProcessWrongPassword() {
        String result = indexController.registerProcess(USERNAME, PASSWORD, PASSWORD_2, "student");

        assertEquals("redirect:/register?bad_password", result);
    }

    @Test
    public void registerProcessSmallPassword() {
        String result = indexController.registerProcess(USERNAME, "123", "123", "student");

        assertEquals("redirect:/register?bad_password", result);
    }

    @Test
    public void registerProcessTeacher() {
        String result = indexController.registerProcess(USERNAME, PASSWORD, PASSWORD, "teacher");

        assertEquals("redirect:/login", result);

        verify(userServiceDa).insertRow(USERNAME, PASSWORD, ROLE_TEACHER_STR);
    }

    @Test
    public void registerProcessAdmin() {
        String result = indexController.registerProcess(USERNAME, PASSWORD, PASSWORD, "admin");

        assertEquals("redirect:/login", result);

        verify(userServiceDa).insertRow(USERNAME, PASSWORD, ROLE_ADMIN_STR);
    }

    @Test (expected = Exception.class)
    public void registerProcessInsertError() {

        doThrow(new Exception())
                .when(userServiceDa)
                .insertRow(USERNAME, PASSWORD, ROLE_ADMIN_STR);

        String result = indexController.registerProcess(USERNAME, PASSWORD, PASSWORD, "admin");

        assertEquals("redirect:/register?invalid_request", result);

        verify(userServiceDa).insertRow(USERNAME, PASSWORD, ROLE_ADMIN_STR);
    }
}