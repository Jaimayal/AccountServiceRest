package com.jaimayal.accountservice.services;

import com.jaimayal.accountservice.entities.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class UserService {
    public void save(User user) {
    }

    public void changePasswordByUserEmail(String email, String password) {
    }

    public Optional<User> getUserById(UUID id) {
        return null;
    }

    public void deleteUserByEmail(String email) {
    }
}
