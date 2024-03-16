package com.java.banksystemproject.service;

import com.java.banksystemproject.model.BankUser;

public interface IBankUserService {

    void register(String userName, String passWord);

    BankUser authenticate(String userName, String passWord);

}
