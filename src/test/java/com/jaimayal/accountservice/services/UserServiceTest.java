package com.jaimayal.accountservice.services;

import com.jaimayal.accountservice.entities.Operation;
import com.jaimayal.accountservice.entities.Role;
import com.jaimayal.accountservice.entities.RoleOperation;
import com.jaimayal.accountservice.entities.User;
import com.jaimayal.accountservice.repositories.UserRepository;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService underTest;
    
    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
    }

    @Test
    void checkUserIsSaved() {
        // given
        User user = new User(
                1L,
                "jaime@gmail.com",
                "12345",
                List.of(Role.USER)
        );

        // when
        underTest.addUser(user);

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
                "jaime@gmail.com",
                oldPassword,
                List.of(Role.ADMINISTRATOR)
        );
        
        String someEmail = "jaime@gmail.com";
        String newPassword = "123";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // when
        underTest.changePasswordByUserEmail(someEmail, newPassword);
        
        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User userCaptured = userCaptor.getValue();
        assertThat(userCaptured.getPassword()).isNotEqualTo(oldPassword);
    }

    @Test
    void checkFindByIdIsInvoked() {
        // given
        User user = new User(
                1L,
                "jaime@gmail.com",
                "12345",
                List.of(Role.ADMINISTRATOR)
        );
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // when
        underTest.getUserById(user.getId());
        
        // then
        verify(userRepository).findById(user.getId());
    }

    @Test
    void checkDeleteMethodIsCalled() {
        // given
        String someEmail = "jaime@gmail.com";
        
        // when
        underTest.deleteUserByEmail(someEmail);
        
        // then
        verify(userRepository).deleteByEmail(anyString());
    }

    @Test
    void checkGrantRoleOperationIsSuccessful() {
        // given
        RoleOperation roleOperation = new RoleOperation(
                "jaime@gmail.com",
                Role.USER,
                Operation.GRANT
        );
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        
        // when
        underTest.updateRolesFollowingOperation(roleOperation);
        
        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User userCaptured = userCaptor.getValue();
        assertThat(userCaptured.getRoles().contains(roleOperation.getRole())).isTrue();
    }

    @Test
    void checkRemoveRoleOperationIsSuccessful() {
        // given
        User user = new User(
                1L,
                "jaime@gmail.com",
                "12345",
                new ArrayList<>(List.of(Role.ADMINISTRATOR))
        );
        
        RoleOperation roleOperation = new RoleOperation(
                "jaime@gmail.com",
                Role.ADMINISTRATOR,
                Operation.REMOVE
        );
        
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // when
        underTest.updateRolesFollowingOperation(roleOperation);

        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User userCaptured = userCaptor.getValue();
        assertThat(userCaptured.getRoles().contains(roleOperation.getRole())).isFalse();
    }
}