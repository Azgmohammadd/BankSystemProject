package com.java.banksystemproject.service.account.factory;

import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.TransactionDaoJDBC;
import com.java.banksystemproject.service.account.ISavingAccountService;
import com.java.banksystemproject.service.account.impl.SavingAccountService;
import com.java.banksystemproject.service.impl.TransactionService;

public class SavingAccountServiceFactory {
    public ISavingAccountService getJDBC(){
        return new SavingAccountService(
                new TransactionService(),
                new BankAccountDaoJDBC(),
                new TransactionDaoJDBC()
        );
    }
}
