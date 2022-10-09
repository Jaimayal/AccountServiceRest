package com.jaimayal.accountservice.services;

import com.jaimayal.accountservice.business.services.UserServiceImpl;
import com.jaimayal.accountservice.persistence.entities.Operation;
import com.jaimayal.accountservice.persistence.entities.Account;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private UserServiceImpl underTest;
    
    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository);
    }

    @Test
    void checkFindByEmailIsInvoked() {
        // given
        User user = new User(
                1L,
                "jaime",
                "ayala",
                "jaime@gmail.com",
                "12345",
                List.of(Account.ADMINISTRATOR)
        );
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // when
        underTest.retrieveUserById(user.getId());

        // then
        verify(userRepository).findById(user.getId());
    }

    @Test
    void checkDeleteByIdIsInvoked() {
        // given
        Long someId = 1L;

        // when
        underTest.deleteUserById(someId);

        // then
        verify(userRepository).deleteById(someId);
    }

    @Test
    void checkUserIsSaved() {
        // given
        User user = new User(
                1L,
                "jaime",
                "ayala",
                "jaime@gmail.com",
                "12345",
                List.of(Account.USER)
        );

        // when
        underTest.registerUser(user);

        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User userCaptured = userCaptor.getValue();
        assertThat(userCaptured).isEqualTo(user);
    }

    @Test
    void checkPasswordChangeIsSuccessful() {
        // given
        String oldPassword = "00000";
        User user = new User(
                1L,
                "jaime",
                "ayala",
                "jaime@gmail.com",
                oldPassword,
                List.of(Account.ADMINISTRATOR)
        );
        
        String someEmail = "jaime@gmail.com";
        String newPassword = "123";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // when
        underTest.updateUserPasswordById(someEmail, newPassword);
        
        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User userCaptured = userCaptor.getValue();
        assertThat(userCaptured.getPassword()).isNotEqualTo(oldPassword);
    }

    @Test
    void checkGrantRoleOperationIsSuccessful() {
        // given
        User user = new User(
                1L,
                "jaime",
                "ayala",
                "jaime@gmail.com",
                "12345",
                new ArrayList<>(List.of(Account.ADMINISTRATOR))
        );

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        
        // when
        underTest.updateUserRolesById(user.getId(), Operation.ADD, List.of("USER"));
        
        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User userCaptured = userCaptor.getValue();
        assertThat(userCaptured.getRoles().contains(Account.USER)).isTrue();
    }

    @Test
    void checkRemoveRoleOperationIsSuccessful() {
        // given
        User user = new User(
                1L,
                "jaime",
                "ayala",
                "jaime@gmail.com",
                "12345",
                new ArrayList<>(List.of(Account.ADMINISTRATOR))
        );
        
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // when
        underTest.updateUserRolesById(user.getId(), Operation.REMOVE, List.of("ADMINISTRATOR"));

        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User userCaptured = userCaptor.getValue();
        assertThat(userCaptured.getRoles().contains(Account.ADMINISTRATOR)).isFalse();
    }
}