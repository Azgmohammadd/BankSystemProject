package com.java.banksystemproject.service.factory;

import com.java.banksystemproject.dao.impl.BankUserDao;
import com.java.banksystemproject.service.IBankUserService;
import com.java.banksystemproject.service.impl.BankUserService;

public class BankUserServiceFactory {

    public IBankUserService get(){
        return new BankUserService(
                new BankUserDao()
        );
    }

}
