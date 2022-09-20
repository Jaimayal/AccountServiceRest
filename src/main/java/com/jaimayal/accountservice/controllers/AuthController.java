package com.jaimayal.accountservice.controllers;

import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        this.userService = userService;
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(final User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/changepass")
    public ResponseEntity<?> changePassword(final Map<String, String> passwordContainer, final Principal principal) {
        String password = passwordContainer.get("password");
        String email = principal.getName();
        userService.changePasswordByUserEmail(email, password);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
