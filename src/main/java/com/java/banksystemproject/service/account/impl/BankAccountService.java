package com.java.banksystemproject.service.account.impl;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.ITransactionDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.service.exception.ExceptionMessageCodes;
import com.java.banksystemproject.service.exception.InsufficientFundsException;
import com.java.banksystemproject.service.account.IBankAccountService;

import com.java.banksystemproject.service.exception.InvalidTransactionException;
import com.java.banksystemproject.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;


@RequiredArgsConstructor
public class BankAccountService implements IBankAccountService {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);
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

        if (transaction.getStatus().equals(TransactionStatus.FAILED)) {
            throw new InvalidTransactionException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_FOR_FEE_TRANSACTION);
        }


        synchronized (lock) {
            this.feeTransaction(account, transaction);

            if (account.getBalance()  < amount) {
                transaction.setStatus(TransactionStatus.FAILED);
                transactionDao.save(transaction);
                this.rollbackFee(account, transaction);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
            }

            bankAccountDao.updateBalance(account, account.getBalance() + amount);
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


        if (transaction.getStatus().equals(TransactionStatus.FAILED)) {
            throw new InvalidTransactionException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_FOR_FEE_TRANSACTION);
        }


        synchronized (lock) {
            this.feeTransaction(account, transaction);

            if (account.getBalance() < amountWithFee) {
                transaction.setStatus(TransactionStatus.FAILED);
                transactionDao.save(transaction);
                this.rollbackFee(account, transaction);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
            }

            bankAccountDao.updateBalance(account, account.getBalance());
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
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_FOR_FEE_TRANSACTION);
            }
            this.feeTransaction(account, transaction);
            bankAccountDao.updateBalance(account, account.getBalance());
        } finally {
            reentrantLock.unlock();
        }

        transaction.setStatus(TransactionStatus.DONE);
        transactionDao.save(transaction);

        return transaction;
    }

    @Override
    public void feeTransaction(BankAccount bankAccount, Transaction transaction) {
        bankAccountDao.updateBalance(bankAccount, bankAccount.getBalance() - transaction.getFee());
    }

    @Override
    public void rollbackFee(BankAccount bankAccount, Transaction transaction) {
        bankAccountDao.updateBalance(bankAccount, bankAccount.getBalance() + transaction.getFee());
    }

    @Override
    public void create(BankAccount bankAccount) {
        try {
            bankAccountDao.save(bankAccount);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Optional<BankAccount> get(BankAccount bankAccount) {
        try {
            return bankAccountDao.get(bankAccount.getAccountNumber());
        }catch (Exception e) {
            logger.error(e.getMessage());
        }

        return Optional.empty();
    }
}

