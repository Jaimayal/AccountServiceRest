package com.jaimayal.accountservice.controllers;

import com.jaimayal.accountservice.dtos.PasswordChange;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class AuthController {
    private final UserService userService;
    
    public AuthController(final UserService userService) {
        Assert.notNull(userService, "UserService must not be null!");
        this.userService = userService;
    }

    /**
     * Registers one new user
     * @param user the user that must be registered into the database
     * @return 200 OK or 409 CONFLICT if email already registered
     * @see User
     */
    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody final User user) {
        userService.addUser(user);
        URI userLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return new ResponseEntity<>(userLocation, HttpStatus.CREATED);
    }

    /**
     * Changes the specified user's password.
     * @param passwordChange Contains the actual user's email and its new password
     * @return 202 ACCEPTED
     * @see PasswordChange
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable final Long id, 
                                            @RequestBody final PasswordChange passwordChange) {
        String newPassword = passwordChange.getNewPassword();
        String oldPassword = passwordChange.getOldPassword();
        userService.updateUserPasswordById(id, oldPassword, newPassword);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
