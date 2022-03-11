package ru.edu.project.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.edu.project.authorization.FrontendUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * UserDetailService.
     */
    @Autowired
    private FrontendUserService frontendUserService;

    /**
     * Configuring access parameters to URL.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .anonymous()
                .authorities("ROLE_ANON")
                .and()
                .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/*").hasAuthority("ROLE_ANON")
                .antMatchers("/student/**").hasAuthority("ROLE_STUDENT")
                .antMatchers("/teacher/**").hasAuthority("ROLE_TEACHER")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .csrf().disable(); //cut of protection from Cross-Site Request Forgery

    }

    /**
     * Configuring users.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(frontendUserService);

    }


}
