package com.jaimayal.accountservice.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@PropertySource(value = "classpath:messages.properties")
public class UserDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @JsonProperty(value = "name")
    @NotBlank(message = "{user.name.notblank}")
    private String name;
    
    @JsonProperty(value = "lastName")
    @NotBlank(message = "{user.lastname.notblank}")
    private String lastName;
    
    @JsonProperty(value = "email")
    @NotBlank(message = "{user.email.not-blank}")
    @Pattern(regexp = "^[\\w\\.-]+@[a-zA-Z-]+(\\.[a-zA-Z]+)+$", 
            message = "{user.email.pattern}")
    private String email;
    
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    @NotBlank(message = "{user.password.notblank}")
    @Size(min = 8, max = 30, message = "{user.password.size}")
    @Pattern(regexp = "^[\\w-.,]+$", 
            message = "{user.password.pattern}")
    private String password;
    
    @JsonProperty(value = "accounts", access = JsonProperty.Access.READ_ONLY)
    private List<String> accounts;
}
