package com.jaimayal.accountservice.presentation.controllers;

import com.jaimayal.accountservice.presentation.dtos.PasswordUpdateDTO;
import com.jaimayal.accountservice.presentation.dtos.UserDTO;
import com.jaimayal.accountservice.presentation.mappers.UserMapper;
import com.jaimayal.accountservice.business.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

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
    
    @PostMapping()
    public ResponseEntity<?> registerUser(@RequestBody @Valid final UserDTO user) {
        Long userId = userService.registerUser(userMapper.fromDtoToEntity(user));
        
        String userLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userId)
                .toUriString();
        
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, userLocation).build();
    }
    
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> updateUserPasswordById(@PathVariable final Long id,
                                                    @RequestBody @Valid final PasswordUpdateDTO passwordUpdateDTO) {
        String newPassword = passwordUpdateDTO.getNewPassword();
        String oldPassword = passwordUpdateDTO.getOldPassword();
        userService.updateUserPasswordById(id, oldPassword, newPassword);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
