package com.java.banksystemproject.dao;

import com.java.banksystemproject.model.BankUser;

import java.util.Optional;

public interface IBankUserDao {

    Optional<BankUser> get(String id);

    void save(BankUser user);

}
