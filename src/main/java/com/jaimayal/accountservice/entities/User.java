package com.jaimayal.accountservice.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Data
@ToString 
@EqualsAndHashCode
public class User {
    @Id
    private UUID id;
    private String email;
    private String password;
    private List<Role> roles;
    
    public void updateRoles(RoleOperation operation) {
        switch (operation.getOperation()) {
            case GRANT:
                roles.add(operation.getRole());
                break;
            case REMOVE:
                roles.remove(operation.getRole());
                break;
        }
    }
    
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
