package com.java.banksystemproject.service.impl;

import com.java.banksystemproject.dao.ITransactionDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.model.constant.TransactionType;
import com.java.banksystemproject.service.ITransactionService;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TransactionService implements ITransactionService {
    private final ITransactionDao transactionDao;
    private double deductFees(double amount, TransactionType transactionType) {
        switch (transactionType) {
            case DEPOSITS, WITHDRAWALS -> {
                double FEE_PERCENT = 0.09;
                return Math.min(Math.max(amount * (1 + FEE_PERCENT), 1200), 10000);
            }
            case GET_BALANCE -> {
                return 1200;
            }
            default -> {
                return 0;
            }
        }
    }

    @Override
    public Transaction createWithdrawTransaction(BankAccount account, double amount) {
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.WITHDRAWALS)
                .transactionDate(new java.sql.Date(new Date().getTime()))
                .amount(amount)
                .sourceAccountNumber(account.getAccountNumber())
                .fee(deductFees(amount, TransactionType.WITHDRAWALS))
                .build();

        if (transaction.getFee() > amount) {
            transaction.setStatus(TransactionStatus.FAILED);
        }

        return transaction;
    }

    @Override
    public Transaction createDepositTransaction(BankAccount account, double amount) {
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSITS)
                .transactionDate(new java.sql.Date(new Date().getTime()))
                .amount(amount)
                .sourceAccountNumber(account.getAccountNumber())
                .fee(deductFees(amount, TransactionType.DEPOSITS))
                .build();
        
        if (transaction.getFee() > amount) {
            transaction.setStatus(TransactionStatus.FAILED);
        }

        return transaction;
    }

    @Override
    public Transaction createGetBalanceTransaction(BankAccount account) {
        return Transaction.builder()
                .transactionType(TransactionType.GET_BALANCE)
                .transactionDate(new java.sql.Date(new Date().getTime()))
                .sourceAccountNumber(account.getAccountNumber())
                .fee(deductFees(0, TransactionType.GET_BALANCE))
                .build();
    }

    @Override
    public Transaction createApplyInterestTransaction(BankAccount account) {
        return Transaction.builder()
                .transactionType(TransactionType.APPLY_INTEREST)
                .transactionDate(new java.sql.Date(new Date().getTime()))
                .sourceAccountNumber(account.getAccountNumber())
                .build();
    }

    @Override
    public Transaction get(String transactionID) {
        Optional<Transaction> transaction = transactionDao.get(Long.parseLong(transactionID));

        return transaction.orElse(null);
    }

    @Override
    public List<Transaction> getAll() {
        return (List<Transaction>) transactionDao.getAll();
    }
}
