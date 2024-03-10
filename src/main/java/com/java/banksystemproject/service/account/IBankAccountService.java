package com.java.banksystemproject.service.account;

import com.java.banksystemproject.model.account.BankAccount;

public interface IBankAccountService {
    void deposit(BankAccount account, double amount);
    void withdraw(BankAccount account, double amount);
    double getBalance(BankAccount account);
}
