package com.example.controller;

import com.example.service.UserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class DemoController {

    private final UserService userService;

    @GetMapping("/public")
    public String publicApi() {
        return "This is a public API";
    }

    @GetMapping("/private")
    public String privateApi(OAuth2AuthenticationToken token) {
        // Persist or update the user in DB on successful auth
        userService.upsertFromOAuth(token);
        Object email = token != null ? token.getPrincipal().getAttribute("email") : null;
        Object name = token != null ? token.getPrincipal().getAttribute("name") : null;
        return "Authenticated as " + (name != null ? name : "Unknown") +
            " (" + (email != null ? email : "no email") + ")";
    }
}

