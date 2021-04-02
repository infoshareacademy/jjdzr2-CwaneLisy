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
                .antMatchers("/css/**", "/templates/**").permitAll()
                .antMatchers("/form").hasRole("USER")
                .antMatchers("/volunteer/**").hasRole("USER")
                .antMatchers("/need-request/**").hasRole("USER")
                .antMatchers("/volunteer/**").hasRole("ADMIN")
                .antMatchers("/need-request/**").hasRole("ADMIN")
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
                .password("{noop}user")
                .roles("USER")
                .and()
                .withUser("Admin")
                .password("{noop}admin")
                .roles("ADMIN");
    }
}
