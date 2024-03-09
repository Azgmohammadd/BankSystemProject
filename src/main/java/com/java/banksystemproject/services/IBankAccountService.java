package com.java.banksystemproject.services;

import com.java.banksystemproject.entities.BankAccount;
import com.java.banksystemproject.services.exception.InsufficientFundsException;
import com.java.banksystemproject.services.exception.InvalidTransactionException;

public interface IBankAccountService {
    void deposit(BankAccount account, double amount);
    void withdraw(BankAccount account, double amount);
    double getBalance(BankAccount account);
}
