package com.java.banksystemproject.service.account.impl;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.ITransactionDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.service.exception.ExceptionMessageCodes;
import com.java.banksystemproject.service.exception.InsufficientFundsException;
import com.java.banksystemproject.service.exception.InvalidTransactionException;
import com.java.banksystemproject.service.impl.TransactionService;

public class CheckingAccountService extends BankAccountService {
    public CheckingAccountService(TransactionService transactionService, IBankAccountDao bankAccountDao, ITransactionDao transactionDao) {
        super(transactionService, bankAccountDao, transactionDao);
    }

    @Override
    public Transaction withdraw(BankAccount account, double amount) {
        if (!(account instanceof CheckingAccount checkingAccount))
            throw new IllegalArgumentException(ExceptionMessageCodes.BSS_INCOMPATIBLE_ACCOUNT_TYPE);

        Transaction transaction = transactionService.createWithdrawTransaction(checkingAccount, amount);
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

            if (checkingAccount.getBalance() + checkingAccount.getOverdraftLimit() < amountWithFee) {
                transaction.setStatus(TransactionStatus.FAILED);
                this.rollbackFee(account, transaction);
                throw new InsufficientFundsException(ExceptionMessageCodes.BSS_INSUFFICIENT_BALANCE_AND_OVER_DRAFT_LIMIT);
            }


            bankAccountDao.updateBalance(checkingAccount, checkingAccount.getBalance() - amount);
        }

        transaction.setStatus(TransactionStatus.DONE);
        transactionDao.save(transaction);
        return transaction;
    }
}

