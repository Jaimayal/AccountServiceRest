package com.jaimayal.accountservice.repositories;

import com.jaimayal.accountservice.persistence.entities.Role;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @Test
    void checkUserCanBeFetchedByEmail() {
        // given
        User user = new User(
                1L,
                "jaime",
                "ayala",
                "jaime@gmail.com",
                "12345",
                List.of(Role.USER)
        );
        underTest.save(user);
        
        // when
        Optional<User> result = underTest.findByEmail(user.getEmail());
        
        // then
        assertThat(result).isPresent();
    }

    @Test
    void checkUserCanBeDeletedByEmail() {
        // given
        User user = new User(
                1L,
                "jaime",
                "ayala",
                "jaime@gmail.com",
                "12345",
                List.of(Role.USER)
        );
        underTest.save(user);
        
        // when
        underTest.deleteByEmail(user.getEmail());
        Optional<User> result = underTest.findById(user.getId());
        
        // then
        assertThat(result).isEmpty();
    }
}