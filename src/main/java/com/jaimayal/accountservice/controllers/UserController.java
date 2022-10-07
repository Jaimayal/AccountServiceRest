package com.jaimayal.accountservice.controllers;

import com.jaimayal.accountservice.dtos.RolesUpdateDTO;
import com.jaimayal.accountservice.dtos.UserDTO;
import com.jaimayal.accountservice.mappers.UserMapper;
import com.jaimayal.accountservice.services.UserService;
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

    /**
     * Gets the all the details of one user by its unique ID.
     * @param id Unique ID linked to one user
     * @return 200 OK or 404 NOT_FOUND if user does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveUserById(@PathVariable final Long id) {
        UserDTO user = userMapper.fromEntityToDto(userService.getUserById(id));
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Gets a List containing all the details of all the users.
     * @return 200 OK 
     */
    @GetMapping()
    public ResponseEntity<?> retrieveAllUsers() {
        List<UserDTO> users = userMapper.fromEntitiesToDtos(userService.getAllUsers());
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /**
     * Deletes one user by its unique ID.
     * @param id Unique ID linked to one user
     * @return 204 NO_CONTENT or 404 NOT_FOUND if user does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable final Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Updates the user roles following the specified RoleOperation.
     * @param rolesUpdateDTO The format of the operation to apply.
     * @param id Unique ID linked to one user
     * @return 200 OK or 404 NOT_FOUND if user does not exist.
     * @see RolesUpdateDTO
     */
    @PatchMapping("/{id}/roles")
    public ResponseEntity<?> updateUserRolesById(@PathVariable final Long id,
                                                 @RequestBody @Valid final RolesUpdateDTO rolesUpdateDTO) {
        userService.updateUserRolesById(id, 
                rolesUpdateDTO.getOperationType(), 
                rolesUpdateDTO.getRoles());
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
