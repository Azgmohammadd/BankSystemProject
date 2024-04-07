package com.java.banksystemproject.service.account;

import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.account.BankAccount;

public interface IBankAccountService {
    Transaction deposit(BankAccount account, double amount);
    Transaction withdraw(BankAccount account, double amount);
    Transaction getBalance(BankAccount account);

    void feeTransaction(BankAccount bankAccount, Transaction transaction);

    void rollbackFee(BankAccount bankAccount, Transaction transaction);

    void create(BankAccount bankAccount);
}
