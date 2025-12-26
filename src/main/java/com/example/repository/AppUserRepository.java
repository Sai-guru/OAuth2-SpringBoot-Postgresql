package com.example.repository;

import com.example.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}

// why this file is that bcos , it defines a repository interface for managing AppUser entities in a Spring Boot application using JPA. The interface extends JpaRepository, providing CRUD operations and a custom method to find users by their email address. This is essential for the UserService class to perform upsert operations based on OAuth2 authentication data.
