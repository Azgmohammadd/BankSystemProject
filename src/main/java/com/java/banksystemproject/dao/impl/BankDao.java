package com.java.banksystemproject.dao.impl;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@RequiredArgsConstructor
public class BankDao implements IBankAccountDao, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Map<String, BankAccount> accountMap;

    @Override
    public Optional<BankAccount> get(String accountNumber) {
        if (accountMap != null && accountNumber != null)
            return Optional.ofNullable(accountMap.get(accountNumber));
        return Optional.empty();
    }

    @Override
    public Collection<BankAccount> getAll() {
        return accountMap.values();
    }

    @Override
    public void save(BankAccount bankAccount) {
        if (accountMap == null)
            accountMap = new HashMap<>();
        if (bankAccount != null)
            accountMap.put(bankAccount.getAccountNumber(), bankAccount);
    }

    @Override
    public void delete(BankAccount bankAccount) {
        if (accountMap != null && bankAccount != null && bankAccount.getAccountNumber() != null)
            accountMap.remove(bankAccount.getAccountNumber());
    }

    @Override
    public void updateBalance(BankAccount account, double amount) {

    }

    @Override
    public void updateMinimumBalance(SavingAccount account, double amount) {

    }
}

