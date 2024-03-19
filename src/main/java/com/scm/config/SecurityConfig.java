package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig {


    @Autowired
    public UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/registration", "/forgot", "/forgot-password", "/do_register", "/send-otp").permitAll()
                        .requestMatchers("/user/**").authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
//        auth
//
//    }
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/user/welcome-user")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );
//                .exceptionHandling(exceptions -> exceptions
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            response.sendRedirect("/404Page");
//                        })
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.sendRedirect("/404Page");
//                        })
//                );


        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
