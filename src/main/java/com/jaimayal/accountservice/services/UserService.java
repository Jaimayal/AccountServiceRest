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
        this.repository.save(user);
    }

    public void changePasswordByUserEmail(String email, String password) {
        Optional<User> possibleUser = this.repository.findByEmail(email);
        if (possibleUser.isEmpty()) {
            throw new RuntimeException("Error user not found");
        }
        
        User user = possibleUser.get();
        user.updatePassword(password);
        this.repository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return this.repository.findById(id);
    }

    public void deleteUserByEmail(String email) {
        this.repository.deleteByEmail(email);
    }

    public void updateRolesFollowingOperation(RoleOperation operation) {
        Optional<User> possibleUser = this.repository.findByEmail(operation.getUserEmail());
        if (possibleUser.isEmpty()) {
            throw new RuntimeException("Error user not found");
        }

        User user = possibleUser.get();
        user.updateRoles(operation);
        this.repository.save(user);
    }
}
