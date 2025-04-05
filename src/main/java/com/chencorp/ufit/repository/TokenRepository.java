package com.chencorp.ufit.repository;

import com.chencorp.ufit.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByTokenAndInactiveIsNull(String token);
}