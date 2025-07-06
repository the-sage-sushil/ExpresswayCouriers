package com.sushil.expressway.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sushil.expressway.entitys.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {    
    Optional<Token> findByToken(String token);
}
