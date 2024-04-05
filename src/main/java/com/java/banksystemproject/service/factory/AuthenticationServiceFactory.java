package com.java.banksystemproject.service.factory;

import com.java.banksystemproject.dao.impl.JDBC.BankUserDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.TokenDaoJDBC;
import com.java.banksystemproject.service.IAuthenticationService;
import com.java.banksystemproject.service.impl.AuthenticationService;

public class AuthenticationServiceFactory {
    public IAuthenticationService get(){
        return null;
    }

    public IAuthenticationService getJDBC(){
        return new AuthenticationService(
                new BankUserDaoJDBC(),
                new TokenDaoJDBC()
        );
    }
}
