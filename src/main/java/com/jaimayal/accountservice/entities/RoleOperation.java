package com.jaimayal.accountservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleOperation {
    @JsonProperty("user")
    private String userEmail;
    private Role role;
    private Operation operation;
}
