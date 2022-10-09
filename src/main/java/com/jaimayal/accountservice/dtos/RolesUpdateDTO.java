package com.jaimayal.accountservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jaimayal.accountservice.entities.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
public class RolesUpdateDTO {
    @JsonProperty(value = "roles")
    @NotEmpty(message = "roles is required and it must not be blank")
    @UniqueElements(message = "roles must contain only unique elements")
    private List<String> roles;
    
    @JsonProperty(value = "operation")
    @NotBlank(message = "operation is required and it must not be blank")
    @Pattern(regexp = "^(REMOVE|GRANT)$", message = "operation must be REMOVE or GRANT")
    private String operationType;
}
