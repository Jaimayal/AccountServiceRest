package com.jaimayal.accountservice.services;

import com.jaimayal.accountservice.entities.RoleOperation;
import com.jaimayal.accountservice.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
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

    public void updateRolesFollowingOperation(RoleOperation operation) {
    }
}
