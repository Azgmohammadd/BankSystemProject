package com.java.banksystemproject.dao.impl;

import com.java.banksystemproject.dao.Dao;
import com.java.banksystemproject.model.account.BankAccount;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Data
@RequiredArgsConstructor
public class BankDao implements Dao<BankAccount>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final Map<String, BankAccount> accountMap;

    @Override
    public Optional<BankAccount> get(BankAccount bankAccount) {
        return Optional.ofNullable(accountMap.get(bankAccount));
    }

    @Override
    public Collection<BankAccount> getAll() {
        return accountMap.values();
    }

    @Override
    public void save(BankAccount bankAccount) {
        accountMap.put(bankAccount.getAccountNumber(), bankAccount);
    }

    @Override
    public void delete(BankAccount bankAccount) {
        accountMap.remove(bankAccount);
    }
}

