package com.jaimayal.accountservice.controllers;

import com.jaimayal.accountservice.dtos.PasswordUpdateDTO;
import com.jaimayal.accountservice.dtos.UserDTO;
import com.jaimayal.accountservice.mappers.UserMapper;
import com.jaimayal.accountservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    
    public AuthController(final UserService userService, final UserMapper userMapper) {
        Assert.notNull(userService, "UserService must not be null!");
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * Registers one new user
     * @param user the user that must be registered into the database
     * @return 200 OK or 409 CONFLICT if email already registered
     * @see UserDTO
     */
    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody @Valid final UserDTO user) {
        userService.addUser(userMapper.fromDtoToEntity(user));
        URI userLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return new ResponseEntity<>(userLocation, HttpStatus.CREATED);
    }

    /**
     * Changes the specified user's password.
     * @param passwordUpdateDTO Contains the actual user's email and its new password
     * @return 202 ACCEPTED
     * @see PasswordUpdateDTO
     */
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> updateUserPasswordById(@PathVariable final Long id,
                                                    @RequestBody @Valid final PasswordUpdateDTO passwordUpdateDTO) {
        String newPassword = passwordUpdateDTO.getNewPassword();
        String oldPassword = passwordUpdateDTO.getOldPassword();
        userService.updateUserPasswordById(id, oldPassword, newPassword);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
