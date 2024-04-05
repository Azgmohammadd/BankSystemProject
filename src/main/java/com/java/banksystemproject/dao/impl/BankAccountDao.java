package com.java.banksystemproject.dao.impl;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.util.impl.JPAUtil;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

public class BankAccountDao implements IBankAccountDao {

    @Override
    public Optional<BankAccount> get(String accountNumber) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Optional<BankAccount> account = Optional.empty();
        try {
            entityManager.getTransaction().begin();
            account = Optional.ofNullable(entityManager.find(BankAccount.class, accountNumber));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
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
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(BankAccount bankAccount) {
        EntityManager entityManager = JPAUtil.getEntityManager();
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
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        BankAccount entity = entityManager.find(BankAccount.class, account.getAccountNumber());
        if (entity != null) {
            entity.setBalance(amount);
            entityManager.merge(entity);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateMinimumBalance(SavingAccount account, double amount) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();

        SavingAccount entity = entityManager.find(SavingAccount.class, account.getAccountNumber());
        if (entity != null) {
            entity.setMinimumBalance(amount);
            entityManager.merge(entity);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
