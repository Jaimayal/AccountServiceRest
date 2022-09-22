package com.jaimayal.accountservice.services;

import com.jaimayal.accountservice.entities.RoleOperation;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public void save(User user) {
    }

    public void changePasswordByUserEmail(String email, String password) {
    }

    public Optional<User> getUserById(UUID id) {
        return null;
    }

    public void deleteUserByEmail(String email) {
    }

    public void updateRolesFollowingOperation(RoleOperation operation) {
    }
}
