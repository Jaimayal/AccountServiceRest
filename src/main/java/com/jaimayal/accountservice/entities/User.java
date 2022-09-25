package com.jaimayal.accountservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString 
@EqualsAndHashCode
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Enumerated
    @CollectionTable
    @ElementCollection
    private List<Role> roles = new ArrayList<>();
    
    public void updateRoles(Operation operation, Role role) {
        switch (operation) {
            case GRANT:
                roles.add(role);
                break;
            case REMOVE:
                roles.remove(role);
                break;
        }
    }
    
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
