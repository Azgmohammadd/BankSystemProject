package com.java.banksystemproject.service.impl;

import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionType;
import com.java.banksystemproject.service.ITransactionService;

import java.util.Date;

public class TransactionService implements ITransactionService {
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
        return Transaction.builder()
                .transactionType(TransactionType.WITHDRAWALS)
                .transactionDate(new Date())
                .amount(amount)
                .sourceAccountNumber(account.getAccountNumber())
                .fee(deductFees(amount, TransactionType.WITHDRAWALS))
                .build();
    }

    @Override
    public Transaction createDepositTransaction(BankAccount account, double amount) {
        return Transaction.builder()
                .transactionType(TransactionType.DEPOSITS)
                .transactionDate(new Date())
                .amount(amount)
                .sourceAccountNumber(account.getAccountNumber())
                .fee(deductFees(amount, TransactionType.DEPOSITS))
                .build();
    }

    @Override
    public Transaction createGetBalanceTransaction(BankAccount account) {
        return Transaction.builder()
                .transactionType(TransactionType.GET_BALANCE)
                .transactionDate(new Date())
                .sourceAccountNumber(account.getAccountNumber())
                .fee(deductFees(0, TransactionType.GET_BALANCE))
                .build();
    }

    @Override
    public Transaction createApplyInterestTransaction(BankAccount account) {
        return Transaction.builder()
                .transactionType(TransactionType.APPLY_INTEREST)
                .transactionDate(new Date())
                .sourceAccountNumber(account.getAccountNumber())
                .build();
    }

}
