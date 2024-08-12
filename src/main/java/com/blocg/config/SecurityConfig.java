package com.blocg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	/*
	 1. auth.anyRequest().authenticated() ensures that any incoming HTTP request must be authenticated,
	    meaning the user must log in.
	 2. This method enables HTTP Basic Authentication. This is a simple authentication mechanism 
	    where the user provides a username and password in the request header, which is then 
	    validated against the configured security context.	 
	 */
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()  // Ensure all requests require authentication
            )
            .httpBasic();  // Enable HTTP Basic Authentication

        return http.build();
    }
}

