package com.jaimayal.accountservice.controllers;

import com.jaimayal.accountservice.entities.PasswordChange;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    
    public AuthController(final UserService userService) {
        Assert.notNull(userService, "UserService must not be null!");
        this.userService = userService;
    }

    /**
     * Registers one new user
     * @param user the user that must be registered into the database
     * @return 200 OK
     * @see User
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(final User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Changes the specified user's password.
     * @param passwordChange Contains the actual user's email and its new password
     * @return 202 ACCEPTED
     * @see PasswordChange
     */
    @PostMapping("/changepass")
    public ResponseEntity<?> changePassword(final PasswordChange passwordChange) {
        String password = passwordChange.getPassword();
        String email = passwordChange.getUserEmail();
        userService.changePasswordByUserEmail(email, password);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
