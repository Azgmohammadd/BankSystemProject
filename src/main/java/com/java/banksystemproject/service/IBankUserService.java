package com.java.banksystemproject.service;

import com.java.banksystemproject.model.BankUser;

public interface IBankUserService {

    void register(BankUser user);

    BankUser authenticate(BankUser user);

}
