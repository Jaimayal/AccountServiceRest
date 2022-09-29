package com.jaimayal.accountservice.controllers;

import com.jaimayal.accountservice.entities.RoleOperation;
import com.jaimayal.accountservice.entities.User;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    
    public UserController(final UserService userService) {
        Assert.notNull(userService, "UserService must not be null!");
        this.userService = userService;
    }

    /**
     * Gets the all the details of one user by its unique ID.
     * @param id Unique ID linked to one user
     * @return 200 OK or 404 NOT_FOUND if user does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveUserById(@PathVariable final Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Gets a List containing all the details of all the users.
     * @return 200 OK 
     */
    @GetMapping()
    public ResponseEntity<?> retrieveAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Deletes one user by its unique ID.
     * @param id Unique ID linked to one user
     * @return 204 NO_CONTENT or 404 NOT_FOUND if user does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable final Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates the user roles following the specified RoleOperation.
     * @param roleOperation The format of the operation to apply.
     * @param id Unique ID linked to one user
     * @return 200 OK or 404 NOT_FOUND if user does not exist.
     * @see RoleOperation
     */
    @PatchMapping("/{id}/roles")
    public ResponseEntity<?> updateUserRolesById(@PathVariable final Long id,
                                                 @RequestBody final RoleOperation roleOperation) {
        userService.updateUserRolesById(id, 
                roleOperation.getOperation(), 
                roleOperation.getRoles());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}