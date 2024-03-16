package com.java.banksystemproject.service.account.factory;

import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.TransactionDaoJDBC;
import com.java.banksystemproject.service.account.IBankAccountService;
import com.java.banksystemproject.service.account.impl.BankAccountService;
import com.java.banksystemproject.service.impl.TransactionService;

public class BankAccountServiceFactory {
    public IBankAccountService getJDBC(){
        return new BankAccountService(
                new TransactionService(),
                new BankAccountDaoJDBC(),
                new TransactionDaoJDBC()
        );
    }
}
