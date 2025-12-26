package com.example.service;

import com.example.entity.AppUser;
import com.example.repository.AppUserRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AppUserRepository repository;

    @Transactional
    public AppUser upsertFromOAuth(OAuth2AuthenticationToken token) {
        if (token == null || token.getPrincipal() == null) {
            return null;
        }
        String email = (String) token.getPrincipal().getAttribute("email");
        String name = (String) token.getPrincipal().getAttribute("name");
        String picture = (String) token.getPrincipal().getAttribute("picture");
        String providerId = (String) token.getPrincipal().getAttribute("sub");
        String provider = token.getAuthorizedClientRegistrationId();

        if (email == null || email.isBlank()) {
            // If email is unavailable, fall back to providerId to avoid duplicate rows
            email = provider + ":" + (providerId != null ? providerId : "unknown");
        }

        Optional<AppUser> existing = repository.findByEmail(email);
        AppUser user = existing.orElseGet(AppUser::new);
        if (existing.isEmpty()) {
            user.setEmail(email);
        }
        user.setName(name);
        user.setPictureUrl(picture);
        user.setProvider(provider);
        user.setProviderId(providerId);
        return repository.save(user);
    }
}
