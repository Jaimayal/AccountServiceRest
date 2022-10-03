package com.jaimayal.accountservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * This class serves as a JSON envelop to receive a List of Roles and a specified Operation that 
 * will be applied to a specific user.
 * @see Operation
 */
@Data
@AllArgsConstructor
public class RolesUpdateDTO {
    @JsonProperty(value = "roles")
    private List<String> roles;
    
    @JsonProperty(value = "operation")
    private Operation operationType;
}
