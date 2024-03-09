package com.java.banksystemproject.services.impl;

import com.java.banksystemproject.entities.BankAccount;
import com.java.banksystemproject.entities.CheckingAccount;
import com.java.banksystemproject.entities.Transaction;
import com.java.banksystemproject.entities.constants.TransactionStatus;
import com.java.banksystemproject.services.exception.ExceptionMessageCodes;
import com.java.banksystemproject.services.exception.InsufficientFundsException;

public class CheckingAccountService extends BankAccountService {
    public CheckingAccountService(TransactionService transactionService) {
        super(transactionService);
    }

    @Override
    public void withdraw(BankAccount account, double amount) {
        CheckingAccount checkingAccount = (CheckingAccount) account;
        Transaction transaction = transactionService.createWithdrawTransaction(account, amount);
        double amountWithFee = transaction.getAmount() + transaction.getFee();

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_INPUT_VALUE);
        }

        if (checkingAccount.getBalance() < amountWithFee) {
            if (checkingAccount.getBalance() - amountWithFee < -checkingAccount.getOverdraftLimit()) {
                transaction.setStatus(TransactionStatus.FAILED);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
            }
        }

        transaction.setStatus(TransactionStatus.DONE);
        checkingAccount.setBalance(checkingAccount.getBalance() - amountWithFee);
    }
}

