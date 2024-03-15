package com.java.banksystemproject.dao;

import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.SavingAccount;

import java.util.Collection;
import java.util.Optional;

public interface IBankAccountDao {
    Optional<BankAccount> get(String id);
    Collection<BankAccount> getAll();
    void save(BankAccount account);
    void delete(BankAccount account);
    void updateBalance(BankAccount account, double amount);
    void updateMinimumBalance(SavingAccount account, double amount);
}
