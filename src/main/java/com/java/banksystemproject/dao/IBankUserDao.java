package com.java.banksystemproject.dao;

import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.account.BankAccount;

import java.util.List;
import java.util.Optional;

public interface IBankUserDao {
    List<BankUser> getAll();

    Optional<BankUser> get(String id);

    void save(BankUser user);

    void addAccount(BankUser bankUser, BankAccount bankAccount);
}
