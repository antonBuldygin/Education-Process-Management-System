package ru.edu.project.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.project.authorization.FrontendUserService;

import java.util.Collection;

@Controller
public class IndexController {

    /**
     * Min password length.
     */
    public static final int MIN_PASS_LENGTH = 4;

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


    /**
     * Frontend user service.
     */
    @Autowired
    private FrontendUserService userServiceDa;

    /**
     * Entry point.
     *
     * @param authentication
     * @return view
     */
    @GetMapping("/")
    public String index(final Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return redirectByRole(authentication.getAuthorities());
        }
        return "index";
    }

    private String redirectByRole(final Collection<? extends GrantedAuthority> authorities) {
        if (authorities.contains(ROLE_STUDENT)) {
            return "redirect:/student/";
        }

        if (authorities.contains(ROLE_TEACHER)) {
            return "redirect:/teacher/";
        }

        if (authorities.contains(ROLE_ADMIN)) {
            return "redirect:/admin/";
        }

        return "index";
    }

    /**
     * Registration form.
     *
     * @return viewname
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Processing of registration form.
     *
     * @param username
     * @param password
     * @param password2
     * @param role
     * @return redirect
     */
    @PostMapping("/register")
    public String registerProcess(
            @RequestParam(name = "username") final String username,
            @RequestParam(name = "password") final String password,
            @RequestParam(name = "password2") final String password2,
            @RequestParam(name = "role") final String role
    ) {
        String userRole = ROLE_STUDENT_STR;

        if ("admin".equals(role)) {
            userRole = ROLE_ADMIN_STR;
        }
        if ("teacher".equals(role)) {
            userRole = ROLE_TEACHER_STR;
        }

        if ("student".equals(role)) {
            userRole = ROLE_STUDENT_STR;
        }

            if (!password.equals(password2) || password.length() < MIN_PASS_LENGTH) {
                return "redirect:/register?bad_password";
            }

            try {
                userServiceDa.insertRow(username, password, userRole);
            } catch (Exception e) {
                return "redirect:/register?invalid_request";
            }
            return "redirect:/login";
        }
    }
