package com.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.config.CustomOAuth2SuccessHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${app.oauth2-redirect-url}")
    private String oauth2RedirectUrl;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/api/public").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .successHandler(customOAuth2SuccessHandler)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        if (allowedOrigins != null) {
            for (String origin : allowedOrigins.split(",")) {
                configuration.addAllowedOrigin(origin.trim());
            }
        }
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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