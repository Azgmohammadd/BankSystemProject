package com.java.banksystemproject.service;

import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.account.BankAccount;

import java.util.List;

public interface IBankUserService {
    BankUser get(String username);

    List<BankUser> getAllUsers();

    void save(BankUser bankUser);

    List<BankAccount> getAllAccounts(BankUser bankUser);

    void addAccount(BankUser bankUser, BankAccount bankAccount);
}
