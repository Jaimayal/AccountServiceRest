package com.jaimayal.accountservice.business.services;

import com.jaimayal.accountservice.persistence.entities.Operation;
import com.jaimayal.accountservice.persistence.entities.Account;
import com.jaimayal.accountservice.persistence.entities.UserEntity;
import com.jaimayal.accountservice.business.errors.exceptions.EmailAlreadyRegisteredException;
import com.jaimayal.accountservice.business.errors.exceptions.PasswordDoesNotMatchException;
import com.jaimayal.accountservice.business.errors.exceptions.UserNotFoundException;
import com.jaimayal.accountservice.persistence.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    
    public UserServiceImpl(UserRepository repository) {
        Assert.notNull(repository, "UserRepository must not be null!");
        this.repository = repository;
    }

    @Override
    public Long registerUser(UserEntity user) {
        boolean exists = this.repository.existsByEmail(user.getEmail());
        if (exists) {
            throw new EmailAlreadyRegisteredException();
        }
        
        this.repository.save(user);
        return user.getId();
    }

    @Override
    @Transactional
    public void updateUserPasswordById(Long id, String oldPassword, String newPassword) {
        Optional<UserEntity> possibleUser = this.repository.findById(id);
        UserEntity user = possibleUser.orElseThrow(UserNotFoundException::new);
        this.updateUserPassword(user, oldPassword, newPassword);
        this.repository.save(user);
    }
    
    private void updateUserPassword(UserEntity user, String oldPassword, String newPassword) {
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            return;
        }
        
        throw new PasswordDoesNotMatchException();
    }

    @Override
    public UserEntity retrieveUserById(Long id) {
        Optional<UserEntity> user = this.repository.findById(id);
        return user.orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        boolean exists = this.repository.existsById(id);
        if (!exists) {
            throw new UserNotFoundException();
        }
        
        this.repository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUserRolesById(Long id, String operationType, List<String> accounts) {
        Optional<UserEntity> possibleUser = this.repository.findById(id);
        UserEntity user = possibleUser.orElseThrow(UserNotFoundException::new);
        this.updateUserRoles(user, operationType, accounts);
        this.repository.save(user);
    }

    @Override
    public List<UserEntity> retrieveUsers(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    private void updateUserRoles(UserEntity user, String operation, List<String> accounts) {
        Operation operationType = Operation.valueOf(operation);
        switch (operationType) {
            case ADD:
                accounts.forEach(account -> user.getAccounts().add(Account.valueOf(account.toUpperCase())));
                break;
            case REMOVE:
                accounts.forEach(account -> user.getAccounts().remove(Account.valueOf(account.toUpperCase())));
                break;
        }
    }
}
