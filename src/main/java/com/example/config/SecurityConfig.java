package com.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/public").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}
// why this file has weird formatting?
// bcos this file does the work of configuring security settings for a Spring Boot application using Spring Security.
//  It defines a SecurityFilterChain bean that specifies which HTTP requests 
// require authentication and which do not. 
// The configuration allows unrestricted access to the 
// "/api/public" endpoint while requiring authentication 
// for all other requests. Additionally, it enables OAuth2 
// login with default settings. The formatting may appear unusual due to the use of method chaining and lambda expressions,
//  which are common in modern Java code to enhance readability 
// and conciseness.