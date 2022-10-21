package com.jaimayal.accountservice.presentation.controllers;

import com.jaimayal.accountservice.presentation.dtos.AccountsUpdateDTO;
import com.jaimayal.accountservice.presentation.dtos.UserDTO;
import com.jaimayal.accountservice.presentation.mappers.UserMapper;
import com.jaimayal.accountservice.business.services.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    
    public UserController(final UserService userService, final UserMapper userMapper) {
        Assert.notNull(userService, "UserService must not be null!");
        this.userService = userService;
        this.userMapper = userMapper;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveUserById(@PathVariable final Long id) {
        UserDTO user = userMapper.fromEntityToDto(userService.retrieveUserById(id));
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    
    @GetMapping()
    public ResponseEntity<?> retrieveUsers(Pageable pageable) {
        List<UserDTO> users = userMapper.fromEntitiesToDtos(userService.retrieveUsers(pageable));
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable final Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PatchMapping("/{id}/accounts")
    public ResponseEntity<?> updateUserAccountsById(@PathVariable final Long id,
                                                    @RequestBody @Valid final AccountsUpdateDTO accountsUpdateDTO) {
        userService.updateUserAccountsById(id, accountsUpdateDTO.getOperation(), accountsUpdateDTO.getAccounts());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
