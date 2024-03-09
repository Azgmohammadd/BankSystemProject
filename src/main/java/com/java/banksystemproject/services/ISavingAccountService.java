package com.java.banksystemproject.services;

import com.java.banksystemproject.entities.SavingAccount;

public interface ISavingAccountService extends IBankAccountService {
    void applyInterest(SavingAccount account);
}
