package com.sva.loan_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .headers(headers -> {
                headers.xssProtection();
                headers.contentSecurityPolicy("script-src 'self'");
                headers.frameOptions().deny();
                // Removed HSTS header since we're not using HTTPS
            })
            .csrf(csrf -> csrf.disable()) // Only for development, enable in production
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        
        return http.build();
    }
}