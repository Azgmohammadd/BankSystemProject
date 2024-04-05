package com.java.banksystemproject.service.impl;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.ITransactionDao;
import com.java.banksystemproject.dao.impl.BankDao;
import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.service.account.ISavingAccountService;
import com.java.banksystemproject.service.account.factory.SavingAccountServiceFactory;
import com.java.banksystemproject.service.account.impl.SavingAccountService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class BankService {
    private static TransactionService transactionService;
    private static IBankAccountDao bankAccountDao;
    private static ITransactionDao transactionDao;

    //Multithreading
    public static BigDecimal calculateBankTotalBalance(BankDao bank) {
        AtomicReference<BigDecimal> totalBalance = new AtomicReference<>(BigDecimal.ZERO);

        if (bank == null || bank.getAll() == null || bank.getAll().isEmpty())
            return totalBalance.get();

        try (ExecutorService executor = Executors.newFixedThreadPool(16)) {

            for (BankAccount account : bank.getAll())
                executor.execute(() ->
                        totalBalance.accumulateAndGet(BigDecimal.valueOf(account.getBalance()), BigDecimal::add));

        } // Adjust the pool size as needed

        return totalBalance.get();
    }

    //Multithreading
    public static void applyInterestToAllAccounts(BankDao bank) {

        if (bank == null || bank.getAll() == null || bank.getAll().isEmpty())
            return;

        try (ExecutorService executor = Executors.newFixedThreadPool(16)) {

        ISavingAccountService service = new SavingAccountServiceFactory().getJDBC();
            for (BankAccount account : bank.getAll())
                if (account instanceof SavingAccount savingAccount)
                    executor.execute(() -> service.applyInterest(savingAccount));

        } // Adjust the pool size as needed
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

        ISavingAccountService service = new SavingAccountServiceFactory().getJDBC();

        bank.getAll().parallelStream()
                .filter(account -> account instanceof SavingAccount)
                .forEach(account -> service.applyInterest((SavingAccount) account));
    }

    public static List<BankAccount> accountsOfUser(BankUser bankUser) {
        return bankAccountDao.getAll().stream()
                .filter(account -> bankUser.getBankAccountsId().contains(account.getAccountNumber()))
                .toList();
    }
}
