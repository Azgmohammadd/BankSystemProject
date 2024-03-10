package com.java.banksystemproject.service.account;

import com.java.banksystemproject.model.account.SavingAccount;

public interface ISavingAccountService extends IBankAccountService {
    void applyInterest(SavingAccount account);
}
