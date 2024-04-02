package com.java.banksystemproject.dao;

import com.java.banksystemproject.model.Token;

import java.util.List;
import java.util.Optional;

public interface ITokenDao {
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);

    void save(Token storedToken);
    void update(Token storedToken);
}
