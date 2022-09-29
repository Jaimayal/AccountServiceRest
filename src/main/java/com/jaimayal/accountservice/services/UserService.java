package com.jaimayal.accountservice.services;

import com.jaimayal.accountservice.entities.Operation;
import com.jaimayal.accountservice.entities.Role;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.exceptions.EmailAlreadyRegisteredException;
import com.jaimayal.accountservice.exceptions.PasswordDoesNotMatchException;
import com.jaimayal.accountservice.exceptions.UserNotFoundException;
import com.jaimayal.accountservice.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {
        Assert.notNull(repository, "UserRepository must not be null!");
        this.repository = repository;
    }
    
    public void addUser(User user) {
        boolean exists = this.repository.existsByEmail(user.getEmail());
        if (exists) {
            throw new EmailAlreadyRegisteredException();
        }
        
        this.repository.save(user);
    }

    @Transactional
    public void updateUserPasswordById(Long id, String oldPassword, String newPassword) {
        Optional<User> possibleUser = this.repository.findById(id);
        User user = possibleUser.orElseThrow(UserNotFoundException::new);
        this.updateUserPassword(user, oldPassword, newPassword);
        this.repository.save(user);
    }

    private void updateUserPassword(User user, String oldPassword, String newPassword) {
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            return;
        }
        
        throw new PasswordDoesNotMatchException();
    }

    public User getUserById(Long id) {
        Optional<User> possibleUser = this.repository.findById(id);
        User user = possibleUser.orElseThrow(UserNotFoundException::new);
        return user;
    }

    @Transactional
    public void deleteUserById(Long id) {
        boolean exists = this.repository.existsById(id);
        if (!exists) {
            throw new UserNotFoundException();
        }
        
        this.repository.deleteById(id);
    }

    @Transactional
    public void updateUserRolesById(Long id, Operation operationType, List<Role> roles) {
        Optional<User> possibleUser = this.repository.findById(id);
        User user = possibleUser.orElseThrow(UserNotFoundException::new);
        this.updateUserRoles(user, operationType, roles);
        this.repository.save(user);
    }

    private void updateUserRoles(User user, Operation operation, List<Role> roles) {
        switch (operation) {
            case GRANT:
                roles.forEach(role -> user.getRoles().add(role));
                break;
            case REMOVE:
                roles.forEach(role -> user.getRoles().remove(role));
                break;
        }
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
