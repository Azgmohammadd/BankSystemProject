package com.java.banksystemproject.dao;

import com.java.banksystemproject.model.Transaction;

import java.util.Collection;
import java.util.Optional;

public interface ITransactionDao {
    Optional<Transaction> get(long id);
    Collection<Transaction> getAll();
    void save(Transaction transaction);
    void delete(Transaction transaction);

}
