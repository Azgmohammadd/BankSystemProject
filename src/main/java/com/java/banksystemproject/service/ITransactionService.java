package com.java.banksystemproject.service;

import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.account.BankAccount;

import java.util.List;

public interface ITransactionService {

    Transaction createWithdrawTransaction(BankAccount account, double amount);

    Transaction createDepositTransaction(BankAccount account, double amount);

    Transaction createGetBalanceTransaction(BankAccount account);

    Transaction createApplyInterestTransaction(BankAccount account);

    Transaction get(String transactionID);

    List<Transaction> getAll();
}
