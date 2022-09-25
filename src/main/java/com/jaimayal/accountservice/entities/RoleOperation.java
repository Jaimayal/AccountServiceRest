package com.jaimayal.accountservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoleOperation {
    @JsonProperty("user")
    private String userEmail;
    private List<Role> roles;
    private Operation operation;
}
