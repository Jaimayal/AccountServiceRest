package com.jaimayal.accountservice.entities;

import lombok.Data;

/**
 * Transient container for a password change in JSON.
 */
@Data
public class PasswordChange {
    private String password;
    private String userEmail;
}
