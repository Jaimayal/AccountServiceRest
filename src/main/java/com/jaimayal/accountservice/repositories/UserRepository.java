package com.jaimayal.accountservice.repositories;

import com.jaimayal.accountservice.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
    boolean existsByEmail(String email);
}
