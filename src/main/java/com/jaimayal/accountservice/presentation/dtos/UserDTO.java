package com.jaimayal.accountservice.presentation.dtos;

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
    
    @JsonProperty(value = "name")
    @NotBlank(message = "name is required and it must not be blank")
    private String name;
    
    @JsonProperty(value = "lastName")
    @NotBlank(message = "lastName is required and it must not be blank")
    private String lastName;
    
    @JsonProperty(value = "email")
    @NotBlank(message = "email is required and it must not be blank")
    @Pattern(regexp = "^[\\w\\.-]+@[a-zA-Z-]+(\\.[a-zA-Z]+)+$", 
            message = "email must contain only letters, " +
                    "one @ and one (or multiple) end domain(s) (.com.es, .us, .com, .arg.io, etc)")
    private String email;
    
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    @NotBlank(message = "password is required and it must not be blank")
    @Size(min = 8, max = 30, message = "password must be within 8 and 30 chars long")
    @Pattern(regexp = "^[\\w-.,]+$", 
            message = "password must contain only letters, digits and _ - . , symbols")
    private String password;
}
