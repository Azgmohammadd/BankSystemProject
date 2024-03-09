package com.java.banksystemproject.services.impl;

import com.java.banksystemproject.entities.BankAccount;
import com.java.banksystemproject.entities.SavingAccount;
import com.java.banksystemproject.entities.Transaction;
import com.java.banksystemproject.entities.constants.TransactionStatus;
import com.java.banksystemproject.services.ISavingAccountService;
import com.java.banksystemproject.services.exception.ExceptionMessageCodes;
import com.java.banksystemproject.services.exception.InsufficientFundsException;
import com.java.banksystemproject.services.exception.InvalidTransactionException;

public class SavingAccountService extends BankAccountService implements ISavingAccountService {
    public SavingAccountService(TransactionService transactionService) {
        super(transactionService);
    }

    @Override
    public void withdraw(BankAccount account, double amount) {
        SavingAccount savingAccount = (SavingAccount) account;
        Transaction transaction = transactionService.createWithdrawTransaction(account, amount);
        double amountWithFee = transaction.getAmount() + transaction.getFee();

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_INPUT_VALUE);
        }

        if (savingAccount.getBalance() - amountWithFee < savingAccount.getMINIMUM_BALANCE()) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new InvalidTransactionException(ExceptionMessageCodes.BSS_MINIMUM_BALANCE_LIMIT);
        }

        if (savingAccount.getBalance() < amountWithFee) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE);
        }

        transaction.setStatus(TransactionStatus.DONE);
        savingAccount.setBalance(savingAccount.getBalance() - amountWithFee);
    }

    @Override
    public void applyInterest(SavingAccount account) {
        double interestAmount = (1 + account.getMonthlyInterestRate()) * account.getMinimumBalanceInMonth();

        account.setBalance(account.getBalance() + interestAmount);
        account.setMinimumBalanceInMonth(account.getBalance());
    }
}

