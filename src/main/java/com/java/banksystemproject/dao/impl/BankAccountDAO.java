package com.java.banksystemproject.dao.impl;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.util.impl.JPAUtil;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

public class BankAccountDAO implements IBankAccountDao {
    private final EntityManager entityManager = JPAUtil.getEntityManager();

    @Override
    public Optional<BankAccount> get(String accountNumber) {
        Optional<BankAccount> account = Optional.empty();
        try {
            entityManager.getTransaction().begin();
            account = Optional.ofNullable(entityManager.find(BankAccount.class, accountNumber));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return account;
    }

    @Override
    public Collection<BankAccount> getAll() {
        return null;
    }

    public void save(BankAccount account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(BankAccount bankAccount) {
        entityManager.getTransaction().begin();

        BankAccount account = entityManager.find(BankAccount.class, bankAccount.getAccountNumber());
        if (account != null) {
            entityManager.remove(account);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateBalance(BankAccount account, double amount) {

    }

    @Override
    public void updateMinimumBalance(SavingAccount account, double amount) {

    }
}
