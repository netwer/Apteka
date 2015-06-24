package aptekaproj.helpers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Admin on 23.06.2015.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/Auth/**").permitAll()
                .antMatchers("/Admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/Doctor/**").hasAuthority("ROLE_DOCTOR")
                .antMatchers("/Apothecary/**").hasAuthority("ROLE_APOTHECARY")
                .antMatchers("/Apothecary/**").hasAuthority("ROLE_APOTHECARY")
                .antMatchers("/Patient/**").hasAuthority("ROLE_PATIENT")
                .antMatchers("/Pharmacies/**").hasAuthority("ROLE_PHARMACIST")
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/Auth/login/")
                .failureUrl("/Auth/error/")
                .usernameParameter("login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/Auth/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/Auth/login/")
                .permitAll()
                .and()
                .rememberMe();
        http.portMapper().http(8080).mapsTo(8443);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}