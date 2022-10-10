package com.jaimayal.accountservice.business.services;

import com.jaimayal.accountservice.business.errors.exceptions.EmailAlreadyRegisteredException;
import com.jaimayal.accountservice.business.errors.exceptions.PasswordDoesNotMatchException;
import com.jaimayal.accountservice.business.errors.exceptions.UserNotFoundException;
import com.jaimayal.accountservice.persistence.entities.Account;
import com.jaimayal.accountservice.persistence.entities.UserEntity;
import com.jaimayal.accountservice.persistence.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl underTest;
    
    @Mock
    private UserRepository repository;
    
    private List<UserEntity> entities;
    
    @BeforeEach
    void setUp() {
        this.entities = new ArrayList<>();
        entities.add(new UserEntity(1L,
                "Jaime",
                "Ayala",
                "jaime@gmail.com",
                "12345678",
                new ArrayList<>()));
        entities.add(new UserEntity(1L,
                "Mark",
                "Schreming",
                "mark@gmail.com",
                "87654321",
                new ArrayList<>()));
        entities.add(new UserEntity(1L,
                "Karla",
                "Ming",
                "karla@gmail.com",
                "12387654",
                new ArrayList<>()));
    }

    @Test
    void checkSaveIsSuccessful() {
        // given
        UserEntity entity = entities.get(0);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(entity);
        
        // when 
        Long registeredUserId = underTest.registerUser(entity);

        // then
        verify(repository).save(entity);
        assertThat(registeredUserId).isNotNull();
        assertThat(registeredUserId).isPositive();
        assertThat(registeredUserId).isEqualTo(entity.getId());
    }

    @Test
    void checkEmailExceptionIsThrownWhenCreatingDuplicatedUser() {
        // given
        UserEntity entity = entities.get(0);
        when(repository.existsByEmail(anyString())).thenReturn(true);

        // when 
        assertThatThrownBy(() -> underTest.registerUser(entity))
                .isInstanceOf(EmailAlreadyRegisteredException.class);

        // then
        verify(repository).existsByEmail(entity.getEmail());
    }

    @Test
    void checkPasswordDoesNotMatchExceptionIsThrownWhenUpdatingWithWrongOldPassword() {
        // given
        UserEntity entity = entities.get(0);
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        // when 
        assertThatThrownBy(() -> underTest.updateUserPasswordById(entity.getId(), "oldpass", "newpass"))
                .isInstanceOf(PasswordDoesNotMatchException.class);

        // then
        verify(repository).findById(entity.getId());
    }

    @Test
    void checkUserNotFoundExceptionIsThrownWhenUpdatingPasswordForNonExistentUser() {
        // given
        UserEntity entity = entities.get(0);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // when 
        assertThatThrownBy(() -> underTest.updateUserPasswordById(entity.getId(), "anyoldpass", "newpass"))
                .isInstanceOf(UserNotFoundException.class);

        // then
        verify(repository).findById(entity.getId());
    }

    @Test
    void checkPasswordUpdateIsSuccessful() {
        // given
        UserEntity entity = entities.get(0);
        String updatedPassword = "pernew123";
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        // when
        underTest.updateUserPasswordById(entity.getId(), entity.getPassword(), updatedPassword);

        // then
        verify(repository).findById(anyLong());
        verify(repository).save(any());
        assertThat(entity.getPassword()).isNotNull();
        assertThat(entity.getPassword()).isEqualTo(updatedPassword);
    }

    @Test
    void checkRetrieveUserReturnsValidUser() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.of(entities.get(0)));
        
        // when
        UserEntity fetchedEntity = underTest.retrieveUserById(entities.get(0).getId());
        
        // then
        verify(repository).findById(fetchedEntity.getId());
        assertThat(fetchedEntity).isNotNull();
        assertThat(fetchedEntity).isEqualTo(entities.get(0));
    }

    @Test
    void checkUserNotFoundExceptionIsThrownWhenRetrievingNonExistentUser() {
        // given
        UserEntity entity = entities.get(0);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // when 
        assertThatThrownBy(() -> underTest.retrieveUserById(entity.getId()))
                .isInstanceOf(UserNotFoundException.class);

        // then
        verify(repository).findById(entity.getId());
    }

    @Test
    void checkDeleteIsCalledSuccessfully() {
        // given
        Long userId = 1L;
        when(repository.existsById(anyLong())).thenReturn(true);
        
        // when
        underTest.deleteUserById(userId);
        
        // then
        verify(repository).existsById(userId);
        verify(repository).deleteById(userId);
    }

    @Test
    void checkUserNotFoundExceptionIsThrownWhenDeletingNonExistentUser() {
        // given
        UserEntity entity = entities.get(0);
        when(repository.existsById(anyLong())).thenReturn(false);

        // when 
        assertThatThrownBy(() -> underTest.deleteUserById(entity.getId()))
                .isInstanceOf(UserNotFoundException.class);

        // then
        verify(repository).existsById(entity.getId());
    }

    @Test
    void checkUpdateAccountsDoesAddAccountsToEntity() {
        // given
        UserEntity entity = entities.get(0);
        String add = "ADD";
        List<String> accountsToAdd = Arrays.asList("facebook", "snapchat", "twitter", 
                "instagram", "reddit", "tiktok");
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        
        // when
        underTest.updateUserAccountsById(entity.getId(), add, accountsToAdd);
        
        // then
        List<Account> testAccounts = accountsToAdd.stream()
                .map(acc -> Account.valueOf(acc.toUpperCase()))
                .collect(Collectors.toList());

        verify(repository).findById(entity.getId());
        verify(repository).save(entity);
        assertThat(entity.getAccounts()).isNotNull();
        assertThat(entity.getAccounts().size()).isGreaterThan(1);
        assertThat(entity.getAccounts().containsAll(testAccounts)).isTrue();
    }

    @Test
    void checkUpdateAccountsDoesRemoveAccountsFromEntity() {
        // given
        UserEntity entity = entities.get(0);
        List<Account> originalUserAccounts = Arrays.asList(Account.FACEBOOK, Account.INSTAGRAM, Account.REDDIT,
                Account.TIKTOK, Account.SNAPCHAT, Account.TWITTER);
        entity.setAccounts(new ArrayList<>(originalUserAccounts));
        
        String remove = "REMOVE";
        List<String> accountsToRemove = Arrays.asList("facebook", "snapchat", "twitter");
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        // when
        underTest.updateUserAccountsById(entity.getId(), remove, accountsToRemove);

        // then
        verify(repository).findById(entity.getId());
        verify(repository).save(entity);
        assertThat(entity.getAccounts()).isNotNull();
        assertThat(entity.getAccounts().size()).isLessThan(originalUserAccounts.size());
        assertThat(entity.getAccounts().contains(Account.FACEBOOK)).isFalse();
        assertThat(entity.getAccounts().containsAll(originalUserAccounts)).isFalse();
    }

    @Test
    void checkUserNotFoundExceptionIsThrownWhenUpdatingAccountsForNonExistentUser() {
        // given
        UserEntity entity = entities.get(0);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // when 
        assertThatThrownBy(() -> underTest.updateUserAccountsById(entity.getId(), 
                "ADD", Arrays.asList("facebook", "Instagram"))).isInstanceOf(UserNotFoundException.class);

        // then
        verify(repository).findById(entity.getId());
    }

    @Test
    void checkRetrieveUsersDoesReturnValidList() {
        // given
        final int pageSize = 10;
        when(repository.findAll(Pageable.ofSize(pageSize))).thenReturn(new PageImpl<>(entities));

        // when
        List<UserEntity> fetchedUserEntities = underTest.retrieveUsers(Pageable.ofSize(pageSize));

        // then
        verify(repository).findAll(Pageable.ofSize(pageSize));
        assertThat(fetchedUserEntities).isNotNull();
        assertThat(fetchedUserEntities.size()).isGreaterThan(1);
        assertThat(fetchedUserEntities.containsAll(entities)).isTrue();
    }
}