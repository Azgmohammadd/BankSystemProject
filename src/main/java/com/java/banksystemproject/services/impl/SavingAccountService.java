package com.java.banksystemproject.services.impl;

import com.java.banksystemproject.entities.BankAccount;
import com.java.banksystemproject.entities.SavingAccount;
import com.java.banksystemproject.entities.Transaction;
import com.java.banksystemproject.entities.constants.TransactionStatus;
import com.java.banksystemproject.exception.InsufficientFundsException;
import com.java.banksystemproject.exception.InvalidTransactionException;

public class SavingAccountService extends BankAccountService {
    public SavingAccountService(TransactionService transactionService) {
        super(transactionService);
    }

    @Override
    public synchronized void withdraw(BankAccount account, double amount) throws InsufficientFundsException, InvalidTransactionException {
        SavingAccount savingAccount = (SavingAccount) account;
        Transaction transaction = transactionService.createWithdrawTransaction(account, amount);
        double amountWithFee = transaction.getAmount() + transaction.getFee();

        if (amount < 0) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new IllegalArgumentException("مقدار برداشتی نمی‌تواند منفی باشد.");
        }

        if (savingAccount.getBalance() - amountWithFee < savingAccount.getMINIMUM_BALANCE()) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new InvalidTransactionException("برداشت زیر حداقل موجودی مجاز نمی‌باشد.");
        }

        if (savingAccount.getBalance() < amountWithFee) {
            transaction.setStatus(TransactionStatus.FAILED);
            throw new InsufficientFundsException("موجودی حساب شما کافی نمی‌باشد.");
        }

        transaction.setStatus(TransactionStatus.DONE);
        savingAccount.setBalance(savingAccount.getBalance() - amountWithFee);
    }

//    public void applyInterest(SavingAccount account) {
//        double interestAmount = (1 + account.getMonthlyInterestRate()) * account.getMinimumBalanceInMonth();
//
//        account.setBalance(account.getBalance() + interestAmount);
//        account.setMinimumBalanceInMonth(account.getBalance());
//    }

//    public void applyInterestAll() {
//        try (ExecutorService executorService = Executors.newFixedThreadPool(5) /*fix number of threads in pool*/) {
//            BankService bankService = new BankService();
//
//            bankService.listAccount()
//                    .stream()
//                    .parallel()
//                    .filter((account -> account instanceof SavingAccount))
//                    .forEach(account -> executorService.submit(() -> this.applyInterest((SavingAccount) account)));
//        }
//    }
}

