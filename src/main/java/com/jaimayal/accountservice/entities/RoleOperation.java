package com.jaimayal.accountservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * This class serves as a JSON envelop to receive a List of Roles and a specified Operation that 
 * will be applied to a specific user.
 * @see Operation
 * @see Role
 */
@Data
@AllArgsConstructor
public class RoleOperation {
    private List<Role> roles;
    private Operation operation;
}
