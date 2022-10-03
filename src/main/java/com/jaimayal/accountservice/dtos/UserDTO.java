package com.jaimayal.accountservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class UserDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @JsonProperty(value = "name", required = true)
    private String name;
    
    @JsonProperty(value = "lastName", required = true)
    private String lastName;
    
    @JsonProperty(value = "email", required = true)
    private String email;
    
    @JsonProperty(value = "password", required = true, access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private String password;
}
