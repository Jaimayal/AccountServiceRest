package com.jaimayal.accountservice.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {
    private User underTest;
    
    @BeforeEach
    void setupUser() {
        underTest = new User(
                1L,
                "jaime@gmail.com",
                "12345",
                new ArrayList<>(List.of(Role.ADMINISTRATOR))
        );
    }
    
    @Test
    void checkGrantRoleOperationIsSuccessful() {
        // when
        underTest.updateRoles(Operation.GRANT, Role.USER);
        
        // then
        assertThat(underTest.getRoles().contains(Role.USER)).isTrue();
    }

    @Test
    void checkRemoveRoleOperationIsSuccessful() {
        // when
        underTest.updateRoles(Operation.REMOVE, Role.ADMINISTRATOR);

        // then
        assertThat(underTest.getRoles().contains(Role.ADMINISTRATOR)).isFalse();
    }

    @Test
    void checkPasswordUpdateIsSuccessful() {
        // given
        String password = "0000";
        
        // when
        underTest.updatePassword(password);
        
        // then
        assertThat(underTest.getPassword().equals(password)).isTrue();
    }
}