package com.java.banksystemproject.service.impl;


import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.IBankUserDao;
import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.service.IBankUserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BankUserService implements IBankUserService {

    private final IBankUserDao bankUserDao;
    private final IBankAccountDao bankAccountDao;

    @Override
    public BankUser get(String username) {
        try {
            var bankUser =  bankUserDao.get(username);

            if (bankUser.isEmpty()) {
                throw new Exception("User not found");
            }
            return bankUser.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BankUser> getAllUsers() {
        try {
            List<BankUser> users = bankUserDao.getAll();

            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(BankUser bankUser) {
        try {
            bankUserDao.save(bankUser);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BankAccount> getAllAccounts(BankUser bankUser) {
        try {
            return bankAccountDao.getAll().stream()
                    .filter(account -> bankUser.getBankAccountsId().contains(account.getAccountNumber()))
                    .toList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addAccount(BankUser bankUser, BankAccount bankAccount) {
        try {
            bankUserDao.addAccount(bankUser, bankAccount);
        } catch (Exception e ){
            e.printStackTrace();
        }
    }
}
