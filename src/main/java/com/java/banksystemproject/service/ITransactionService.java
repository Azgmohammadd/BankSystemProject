package com.java.banksystemproject.service;

import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.account.BankAccount;

public interface ITransactionService {

    Transaction createWithdrawTransaction(BankAccount account, double amount);

    Transaction createDepositTransaction(BankAccount account, double amount);

    Transaction createGetBalanceTransaction(BankAccount account);
}
