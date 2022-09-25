package com.jaimayal.accountservice.services;

import com.jaimayal.accountservice.entities.Operation;
import com.jaimayal.accountservice.entities.Role;
import com.jaimayal.accountservice.entities.RoleOperation;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.exceptions.UserNotFoundException;
import com.jaimayal.accountservice.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {
        Assert.notNull(repository, "The UserRepository must not be null!");
        this.repository = repository;
    }
    
    public void addUser(User user) {
        this.repository.save(user);
    }

    public void changePasswordByUserEmail(String email, String password) {
        Optional<User> possibleUser = this.repository.findByEmail(email);
        User user = possibleUser.orElseThrow(UserNotFoundException::new);
        this.updateUserPassword(user, password);
        this.repository.save(user);
    }

    public User getUserById(Long id) {
        Optional<User> possibleUser = this.repository.findById(id);
        User user = possibleUser.orElseThrow(UserNotFoundException::new);
        return user;
    }

    public void deleteUserByEmail(String email) {
        this.repository.deleteByEmail(email);
    }

    public void updateUserRolesByEmail(String email, Operation operationType, Role role) {
        Optional<User> possibleUser = this.repository.findByEmail(email);
        User user = possibleUser.orElseThrow(UserNotFoundException::new);
        this.updateUserRoles(user, operationType, role);
        this.repository.save(user);
    }

    private void updateUserRoles(User user, Operation operation, Role role) {
        switch (operation) {
            case GRANT:
                user.getRoles().add(role);
                break;
            case REMOVE:
                user.getRoles().remove(role);
                break;
        }
    }

    private void updateUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }
}
