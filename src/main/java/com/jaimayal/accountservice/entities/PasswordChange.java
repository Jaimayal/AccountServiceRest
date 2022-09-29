package com.jaimayal.accountservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Transient container for a password change in JSON.
 * 
 * @Attributes password - The new password 
 * userEmail - The user whose password is going to be changed
 */
@Data
@AllArgsConstructor
public class PasswordChange {
    private String oldPassword;
    private String newPassword;
}
