package com.java.banksystemproject.service.factory;

import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.BankUserDaoJDBC;
import com.java.banksystemproject.service.IBankUserService;
import com.java.banksystemproject.service.impl.BankUserService;

public class BankUserServiceFactory {

    public IBankUserService get(){
        return new BankUserService(
                new BankUserDaoJDBC(),
                new BankAccountDaoJDBC()
        );
    }

}
