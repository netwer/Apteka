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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

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
                .antMatchers("/", "/api/auth*").permitAll()
                .antMatchers("/admin*").hasAuthority("ROLE_ADMIN")
                .antMatchers("/doctor*").hasAuthority("ROLE_DOCTOR")
                .antMatchers("/apothecary*").hasAuthority("ROLE_APOTHECARY")
                .antMatchers("/patient*").hasAuthority("ROLE_PATIENT")
                .antMatchers("/pharmacies*").hasAuthority("ROLE_PHARMACIST")
                .antMatchers("/api*").fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/api/auth/login/")
                .failureUrl("/api/auth/error/")
                .usernameParameter("login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/auth/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/auth/login/")
                .permitAll()
                .and()
                .rememberMe()
                .and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                .csrf().csrfTokenRepository(csrfTokenRepository());;
        http.portMapper().http(8080).mapsTo(8443);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

}