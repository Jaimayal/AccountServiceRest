package com.jaimayal.accountservice.persistence.repositories;

import com.jaimayal.accountservice.persistence.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.save(new UserEntity(1L, 
                "Jaime", 
                "Ayala", 
                "jaime@gmail.com", 
                "12345678", 
                Collections.emptyList()));
    }

    @Test
    void checkValidUserExistsByEmail() {
        // given
        String validEmail = "jaime@gmail.com";
        
        // when
        boolean exists = underTest.existsByEmail(validEmail);
        
        // then
        assertThat(exists).isTrue();
    }

    @Test
    void checkInvalidUserDoesNotExistByEmail() {
        // given
        String invalidEmail = "mark@gmail.com";
        
        // when
        boolean exists = underTest.existsByEmail(invalidEmail);

        // then
        assertThat(exists).isFalse();
    }
}