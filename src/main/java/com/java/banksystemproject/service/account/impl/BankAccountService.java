package com.java.banksystemproject.service.account.impl;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.ITransactionDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.service.exception.ExceptionMessageCodes;
import com.java.banksystemproject.service.exception.InsufficientFundsException;
import com.java.banksystemproject.service.account.IBankAccountService;

import com.java.banksystemproject.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.locks.ReentrantLock;


@RequiredArgsConstructor
public class BankAccountService implements IBankAccountService {
    protected final TransactionService transactionService;
    protected final IBankAccountDao bankAccountDao;
    protected final ITransactionDao transactionDao;
    protected final Object lock = new Object();
    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public Transaction deposit(BankAccount account, double amount) {
        Transaction transaction = transactionService.createDepositTransaction(account, amount);

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionDao.save(transaction);
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);
        }

        synchronized (lock) {
            if (account.getBalance() + amount < transaction.getFee()) {
                transaction.setStatus(TransactionStatus.FAILED);
                transactionDao.save(transaction);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_AND_AMOUNT_TO_PAY_FEE);
            }
            bankAccountDao.updateBalance(account, account.getBalance() + amount - transaction.getFee());
        }

        transaction.setStatus(TransactionStatus.DONE);
        transactionDao.save(transaction);

        return transaction;
    }

    @Override
    public Transaction withdraw(BankAccount account, double amount) {
        Transaction transaction = transactionService.createWithdrawTransaction(account, amount);
        double amountWithFee = transaction.getAmount() + transaction.getFee();

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionDao.save(transaction);
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);
        }

        synchronized (lock) {
            if (account.getBalance() < amountWithFee) {
                transaction.setStatus(TransactionStatus.FAILED);
                transactionDao.save(transaction);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
            }
            bankAccountDao.updateBalance(account, account.getBalance() - amountWithFee);
        }

        transaction.setStatus(TransactionStatus.DONE);
        transactionDao.save(transaction);

        return transaction;
    }

    @Override
    public Transaction getBalance(BankAccount account) {
        Transaction transaction = transactionService.createGetBalanceTransaction(account);
        reentrantLock.lock();
        try {
            if (account.getBalance() < transaction.getFee()) {
                transaction.setStatus(TransactionStatus.FAILED);
                transactionDao.save(transaction);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
            }
            bankAccountDao.updateBalance(account, account.getBalance() - transaction.getFee());
        } finally {
            reentrantLock.unlock();
        }

        transaction.setStatus(TransactionStatus.DONE);
        transactionDao.save(transaction);

        return transaction;
    }

    @Override
    public void create(BankAccount bankAccount) {
        try {
            bankAccountDao.save(bankAccount);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

