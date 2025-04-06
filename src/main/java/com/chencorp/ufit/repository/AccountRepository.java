package com.chencorp.ufit.repository;

import com.chencorp.ufit.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.chencorp.ufit.model.User;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByPhone(String phone); 
    Optional<Account> findByEmail(String email); 
    Optional<Account> findByUserId(Integer user);
}