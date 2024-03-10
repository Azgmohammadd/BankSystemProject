package com.java.banksystemproject.service.impl;

import com.java.banksystemproject.dao.impl.BankDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.service.account.ISavingAccountService;
import com.java.banksystemproject.service.account.impl.SavingAccountService;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class BankService {

    //Multithreading
    public static BigDecimal calculateBankTotalBalance(BankDao bank) {
        AtomicReference<BigDecimal> totalBalance = new AtomicReference<>(BigDecimal.ZERO);

        if (bank == null || bank.getAll() == null || bank.getAll().isEmpty())
            return totalBalance.get();

        ExecutorService executor = Executors.newFixedThreadPool(16); // Adjust the pool size as needed

        for (BankAccount account : bank.getAll())
            executor.execute(() ->
                    totalBalance.accumulateAndGet(BigDecimal.valueOf(account.getBalance()), BigDecimal::add));

        executor.shutdown();
        while (!executor.isTerminated()) {
            //Wait Until Executing Ends
        }

        return totalBalance.get();
    }

    //Multithreading
    public static void applyInterestToAllAccounts(BankDao bank) {

        if (bank == null || bank.getAll() == null || bank.getAll().isEmpty())
            return;

        ExecutorService executor = Executors.newFixedThreadPool(16); // Adjust the pool size as needed

        ISavingAccountService service = new SavingAccountService(new TransactionService());

        for (BankAccount account : bank.getAll())
            if (account instanceof SavingAccount savingAccount)
                executor.execute(() -> service.applyInterest(savingAccount));

        executor.shutdown();
        while (!executor.isTerminated()) {
            //Wait Until Executing Ends
        }
    }

    //Lambda And Stream
    public static BigDecimal getSumOfHighValueBalances(BankDao bank, double minimumBalance) {

        if (bank == null || bank.getAll() == null || bank.getAll().isEmpty())
            return BigDecimal.ZERO;

        return bank.getAll().parallelStream()
                .filter(account -> account.getBalance() >= minimumBalance)
                .map(account -> BigDecimal.valueOf(account.getBalance()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //Lambda And Stream
    public static void filterSavingAccountsAndApplyInterest(BankDao bank){

        if (bank == null || bank.getAll() == null || bank.getAll().isEmpty())
            return;

        ISavingAccountService service = new SavingAccountService(new TransactionService());

        bank.getAll().parallelStream()
                .filter(account -> account instanceof SavingAccount)
                .forEach(account -> service.applyInterest((SavingAccount) account));
    }
}
