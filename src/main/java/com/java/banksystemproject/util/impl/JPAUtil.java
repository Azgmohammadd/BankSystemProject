package com.java.banksystemproject.util.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("BankSystem");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

}
