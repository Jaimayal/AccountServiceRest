package com.jaimayal.accountservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Transient container for a password change in JSON.
 */
@Data
@AllArgsConstructor
public class PasswordChange {
    private String password;
    private String userEmail;
}
