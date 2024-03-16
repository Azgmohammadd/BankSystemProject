package com.java.banksystemproject.service.account.factory;

import com.java.banksystemproject.dao.impl.BankAccountDao;
import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.TransactionDaoJDBC;
import com.java.banksystemproject.dao.impl.TransactionDao;
import com.java.banksystemproject.service.account.IBankAccountService;
import com.java.banksystemproject.service.account.impl.CheckingAccountService;
import com.java.banksystemproject.service.impl.TransactionService;

public class CheckingAccountServiceFactory {

    public IBankAccountService get(){
        return new CheckingAccountService(
                new TransactionService(),
                new BankAccountDao(),
                new TransactionDao()
        );
    }

    public IBankAccountService getJDBC() {
        return new CheckingAccountService(
                new TransactionService(),
                new BankAccountDaoJDBC(),
                new TransactionDaoJDBC()
        );
    }
}
