package com.jaimayal.accountservice.repositories;

import com.jaimayal.accountservice.entities.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
}
