package com.example.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class HomeController {

    private final UserService userService;
    
    @GetMapping("/")
    public String home(OAuth2AuthenticationToken token) {
        if (token != null) {
            userService.upsertFromOAuth(token);
            Object email = token.getPrincipal().getAttribute("email");
            Object name = token.getPrincipal().getAttribute("name");
            return "Welcome " + (name != null ? name : "User") +
                    " (" + (email != null ? email : "no email") +
                    ")! You are authenticated via Google OAuth2.";
        }
        return "Welcome! Visit /api/private to login.";
    }
}
