package com.jaimayal.accountservice.controllers;

import com.jaimayal.accountservice.entities.RoleOperation;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(final UserService userService) {
        Assert.notNull(userService, "UserService must not be null!");
        this.userService = userService;
    }

    /**
     * Gets the all the details of one user by its unique ID.
     * @param id Unique ID of an user
     * @return 200 OK or 404 NOT_FOUND 
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<?> retrieveUserById(@PathVariable final UUID id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Deletes all the users linked to one email.
     * @param email The email linked to one or multiple users
     * @return 202 ACCEPTED
     */
    @DeleteMapping("/user/{email}")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable final String email) {
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Updates an user role's following the specified operation
     * @param operation The format of the OPERATION, containing the user's email, the role 
     *                  and the type of operation.
     * @return 200 OK or 404 NOT_FOUND if user is not found.
     * @see RoleOperation
     */
    @PutMapping
    public ResponseEntity<?> updateUserRoles(@RequestBody final RoleOperation operation) {
        userService.updateRolesFollowingOperation(operation);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
