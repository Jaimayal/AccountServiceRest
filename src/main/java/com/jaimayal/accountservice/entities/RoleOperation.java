package com.jaimayal.accountservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleOperation {
    @JsonProperty("user")
    private String userEmail;
    private Role role;
    private Operation operation;
}
