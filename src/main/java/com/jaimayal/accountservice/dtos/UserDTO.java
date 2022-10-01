package com.jaimayal.accountservice.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class UserDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private List<String> roles;
}
