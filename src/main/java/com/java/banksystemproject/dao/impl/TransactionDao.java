package com.java.banksystemproject.dao.impl;

import com.java.banksystemproject.dao.ITransactionDao;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.util.impl.JPAUtil;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

public class TransactionDao implements ITransactionDao {
    private final EntityManager entityManager = JPAUtil.getEntityManager();

    @Override
    public Optional<Transaction> get(long id) {
        Optional<Transaction> transaction = Optional.empty();
        try {
            entityManager.getTransaction().begin();
            transaction = Optional.ofNullable(entityManager.find(Transaction.class, id));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
        return transaction;
    }

    @Override
    public Collection<Transaction> getAll() {
        return null;
    }

    @Override
    public void save(Transaction transaction) {
        entityManager.getTransaction().begin();
        entityManager.persist(transaction);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Transaction transaction) {
        entityManager.getTransaction().begin();

        Transaction entity = entityManager.find(Transaction.class, transaction.getTransactionId());
        if (entity != null) {
            entityManager.remove(entity);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
