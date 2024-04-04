package com.java.banksystemproject.service.account.factory;

import com.java.banksystemproject.dao.impl.BankAccountDao;
import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.TransactionDaoJDBC;
import com.java.banksystemproject.dao.impl.TransactionDao;
import com.java.banksystemproject.service.account.ISavingAccountService;
import com.java.banksystemproject.service.account.impl.SavingAccountService;
import com.java.banksystemproject.service.impl.TransactionService;

public class SavingAccountServiceFactory {

    public ISavingAccountService get(){
        return new SavingAccountService(
                new TransactionService(new TransactionDaoJDBC()),
                new BankAccountDao(),
                new TransactionDao()
        );
    }

    public ISavingAccountService getJDBC(){
        return new SavingAccountService(
                new TransactionService(new TransactionDaoJDBC()),
                new BankAccountDaoJDBC(),
                new TransactionDaoJDBC()
        );
    }
}
