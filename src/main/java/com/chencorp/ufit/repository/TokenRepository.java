package com.chencorp.ufit.repository;

import com.chencorp.ufit.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
import com.chencorp.ufit.model.User;


public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByTokenAndInactiveIsNull(String token);
    List<Token> findAllByUser(User user);
}