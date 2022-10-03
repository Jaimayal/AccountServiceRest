package com.jaimayal.accountservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
@ToString
public class UserDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @JsonProperty(value = "name", required = true)
    @NotBlank(message = "Name is required")
    private String name;
    
    @JsonProperty(value = "lastName", required = true)
    @NotBlank(message = "lastName is required")
    private String lastName;
    
    @JsonProperty(value = "email", required = true)
    @NotBlank(message = "email is required")
    @Pattern(regexp = "^[\\w\\.-]+@[a-zA-Z-]+(\\.[a-zA-Z]+)+$", 
            message = "email must contain only letters, " +
                    "one @ and one (or multiple) end domain(s) (.com.es, .us, .com, .arg.io, etc)")
    private String email;
    
    @JsonProperty(value = "password", required = true, access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    @NotBlank(message = "password is required")
    @Size(min = 8, max = 30, message = "password must be within 8 and 30 chars long")
    @Pattern(regexp = "^[\\w-.,]+$", 
            message = "password must contain only letters, digits and _ - . , symbols")
    private String password;
}
