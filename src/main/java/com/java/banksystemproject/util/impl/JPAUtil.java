package com.java.banksystemproject.util.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "BankSystem";
    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory.createEntityManager();
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }

}
