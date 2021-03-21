package com.infoshare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/login","/").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/form").hasRole("USER")
                .antMatchers("/list").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().defaultSuccessUrl("/list")
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("User")
                .password("{bcrypt}$2y$12$6I3PgkTabk1KIbzHWWMKu.6DY3ARBYiNBFQPCYNFmaaaXC7pW5XDm")
                .roles("USER")
                .and()
                .withUser("Admin")
                .password("{bcrypt}$2y$12$KoHmPW6s4TQPVIiYMTzb2ODfvwMhEueHYLO1o2WoRVQRw68NIxZb6")
                .roles("ADMIN");
    }




}
