package com.jaimayal.accountservice.services;

import com.jaimayal.accountservice.entities.RoleOperation;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.exceptions.UserNotFoundException;
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
        User user = possibleUser.orElseThrow(UserNotFoundException::new);
        user.updatePassword(password);
        this.repository.save(user);
    }

    public User getUserById(UUID id) {
        Optional<User> possibleUser = this.repository.findById(id);
        User user = possibleUser.orElseThrow(UserNotFoundException::new);
        return user;
    }

    public void deleteUserByEmail(String email) {
        this.repository.deleteByEmail(email);
    }

    public void updateRolesFollowingOperation(RoleOperation operation) {
        Optional<User> possibleUser = this.repository.findByEmail(operation.getUserEmail());
        User user = possibleUser.orElseThrow(UserNotFoundException::new);
        user.updateRoles(operation);
        this.repository.save(user);
    }
}
