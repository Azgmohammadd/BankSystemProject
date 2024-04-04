package com.java.banksystemproject.service.account.impl;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.ITransactionDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.service.account.ISavingAccountService;
import com.java.banksystemproject.service.exception.ExceptionMessageCodes;
import com.java.banksystemproject.service.exception.InvalidTransactionException;
import com.java.banksystemproject.service.impl.TransactionService;

public class SavingAccountService extends BankAccountService implements ISavingAccountService {
    public SavingAccountService(TransactionService transactionService, IBankAccountDao bankAccountDao, ITransactionDao transactionDao) {
        super(transactionService, bankAccountDao, transactionDao);
    }

    @Override
    public Transaction withdraw(BankAccount account, double amount) {
        if (!(account instanceof SavingAccount savingAccount))
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_INCOMPATIBLE_ACCOUNT_TYPE);

        Transaction transaction = transactionService.createWithdrawTransaction(savingAccount, amount);
        double amountWithFee = transaction.getAmount() + transaction.getFee();

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionDao.save(transaction);
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_NEGATIVE_AMOUNT);
        }

        synchronized (lock) {
            if (savingAccount.getBalance() < amountWithFee + savingAccount.getMinimumBalance()) {
                transaction.setStatus(TransactionStatus.FAILED);
                transactionDao.save(transaction);
                throw new InvalidTransactionException(ExceptionMessageCodes.BSS_MINIMUM_BALANCE_LIMIT);
            }
            bankAccountDao.updateBalance(account, account.getBalance() - amountWithFee);
        }

        transaction.setStatus(TransactionStatus.DONE);
        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public void applyInterest(SavingAccount account) {
        Transaction transaction = transactionService.createApplyInterestTransaction(account);

        double interestAmount = (1 + account.getMonthlyInterestRate()) * account.getMinimumBalanceInMonth();

        synchronized (lock) {
            bankAccountDao.updateBalance(account, account.getBalance() + interestAmount);
            bankAccountDao.updateMinimumBalance(account, account.getBalance());
        }

        transaction.setStatus(TransactionStatus.DONE);
        transactionDao.save(transaction);
    }
}

