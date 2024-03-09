package com.java.banksystemproject.services.impl;

import com.java.banksystemproject.entities.BankAccount;
import com.java.banksystemproject.entities.Transaction;
import com.java.banksystemproject.entities.constants.TransactionStatus;
import com.java.banksystemproject.services.exception.ExceptionMessageCodes;
import com.java.banksystemproject.services.exception.InsufficientFundsException;
import com.java.banksystemproject.services.exception.InvalidTransactionException;
import com.java.banksystemproject.services.IBankAccountService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BankAccountService implements IBankAccountService {
    protected final TransactionService transactionService;

    @Override
    public void deposit(BankAccount account, double amount) {
        Transaction transaction = transactionService.createDepositTransaction(account, amount);

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_INPUT_VALUE);
        }

        transaction.setStatus(TransactionStatus.DONE);

        account.setBalance(account.getBalance() + amount - transaction.getFee());
    }

    @Override
    public synchronized void withdraw(BankAccount account, double amount) {
        Transaction transaction = transactionService.createWithdrawTransaction(account, amount);
        double amountWithFee = transaction.getAmount() + transaction.getFee();

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);
        }

        if (account.getBalance() < amountWithFee) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
        }

        transaction.setStatus(TransactionStatus.DONE);
        account.setBalance(account.getBalance() - amountWithFee);
    }

    @Override
    public double getBalance(BankAccount account) {
        Transaction transaction = transactionService.createGetBalanceTransaction(account);

        if (account.getBalance() < transaction.getFee()) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
        }

        return account.getBalance();
    }
}

