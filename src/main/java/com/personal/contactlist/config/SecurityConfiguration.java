package com.personal.contactlist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author adhabriy
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Profile({"dev", "test", "production"})
public class SecurityConfiguration {

    // Authentication

    // method name is not important as long as it's autowired as it takes into authentication manager builder and now we can share this user with both the
    // authorization
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // we can use jdbcAuthentication or ldapAuthentication
        // this is shared between both the static classes

        auth.inMemoryAuthentication()
                .withUser("user")
                .password("password")
                .roles("USER", "ADMIN", "API_USER")
                .and()
                .withUser("joe")
                .password("password")
                .roles("API_USER");


    }

    // Authorization

    @Configuration
    @Order(1)     // evaluate lowest number first
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {

            http.
                    antMatcher("/api/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("API_USER")
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable();


        }
    }

    @Configuration
//    @Order(100) default order is 100
    public static class FormLoginWebSecurityConfigureAdapter extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/h2-console/**", "css/**", "/index.html", "/favicon.*", "/books");
        }

        public void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/**").authorizeRequests()
                    .anyRequest().hasRole("USER")
                    .and()
                    .formLogin()
                    .loginPage("/login")  // we have defined the view for this url in WebConfig.java
                    .permitAll()
                    .failureUrl("/login?error=1")
                    .loginProcessingUrl("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/login?logout");


        }
    }


}
