package com.java.banksystemproject.service.account.impl;

import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.service.exception.ExceptionMessageCodes;
import com.java.banksystemproject.service.exception.InsufficientFundsException;
import com.java.banksystemproject.service.account.IBankAccountService;

import com.java.banksystemproject.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BankAccountService implements IBankAccountService {
    protected final TransactionService transactionService;
    protected final Object lock = new Object();

    @Override
    public void deposit(BankAccount account, double amount) {
        Transaction transaction = transactionService.createDepositTransaction(account, amount);

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);
        }

        synchronized (lock) {
            if (account.getBalance() + amount < transaction.getFee()) {
                transaction.setStatus(TransactionStatus.FAILED);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_AND_AMOUNT_TO_PAY_FEE);
            }
            account.setBalance(account.getBalance() + amount - transaction.getFee());
        }

        transaction.setStatus(TransactionStatus.DONE);
    }

    @Override
    public synchronized void withdraw(BankAccount account, double amount) {
        Transaction transaction = transactionService.createWithdrawTransaction(account, amount);
        double amountWithFee = transaction.getAmount() + transaction.getFee();

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);
        }

        synchronized (lock) {
            if (account.getBalance() < amountWithFee) {
                transaction.setStatus(TransactionStatus.FAILED);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
            }
            account.setBalance(account.getBalance() - amountWithFee);
        }

        transaction.setStatus(TransactionStatus.DONE);
    }

    @Override
    public double getBalance(BankAccount account) {
        Transaction transaction = transactionService.createGetBalanceTransaction(account);

        synchronized (lock) {
            if (account.getBalance() < transaction.getFee()) {
                transaction.setStatus(TransactionStatus.FAILED);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
            }
            account.setBalance(account.getBalance() - transaction.getFee());
        }

        transaction.setStatus(TransactionStatus.DONE);

        return account.getBalance();
    }
}

