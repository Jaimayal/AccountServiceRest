package com.jaimayal.accountservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    @NotEmpty(message = "roles must not be empty")
    @UniqueElements(message = "roles must contain only unique elements")
    private List<String> roles;
    
    @JsonProperty(value = "operation")
    @Pattern(regexp = "^(REMOVE|GRANT)$", message = "operation must be REMOVE or GRANT")
    private Operation operationType;
}
