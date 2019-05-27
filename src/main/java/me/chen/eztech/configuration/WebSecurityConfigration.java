package me.chen.eztech.configuration;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfigration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.queries.users-query}")
    private String usersQuery;
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin/**").permitAll()
                    .and()
                .formLogin()
                    .loginPage("/login").defaultSuccessUrl("/admin/dashboard")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }
}
