package com.jaimayal.accountservice.business.services;

import com.jaimayal.accountservice.persistence.entities.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Long registerUser(UserEntity user);
    void updateUserPasswordById(Long id, String oldPassword, String newPassword);
    UserEntity retrieveUserById(Long id);
    void deleteUserById(Long id);
    void updateUserRolesById(Long id, String operationType, List<String> roles);
    List<UserEntity> retrieveUsers(Pageable pageable);
}
