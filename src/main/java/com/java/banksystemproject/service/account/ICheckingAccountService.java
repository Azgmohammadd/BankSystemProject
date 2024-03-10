package com.java.banksystemproject.service.account;

import com.java.banksystemproject.model.account.CheckingAccount;

public interface ICheckingAccountService extends IBankAccountService {
    void withdraw(CheckingAccount account, double amount);
}
