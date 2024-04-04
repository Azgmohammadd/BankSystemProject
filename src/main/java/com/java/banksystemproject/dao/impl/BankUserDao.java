package com.java.banksystemproject.dao.impl;

import com.java.banksystemproject.dao.IBankUserDao;
import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.util.impl.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class BankUserDao implements IBankUserDao {

    @Override
    public List<BankUser> getAll() {
        return null;
    }

    @Override
    public Optional<BankUser> get(String userName) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        Optional<BankUser> user = Optional.empty();
        try {
            entityManager.getTransaction().begin();
            user = Optional.ofNullable(entityManager.find(BankUser.class, userName));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
        return user;
    }

    @Override
    public void save(BankUser user) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
