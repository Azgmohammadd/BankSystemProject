package com.java.banksystemproject.dao.impl.JDBC;

import com.java.banksystemproject.dao.ITransactionDao;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.model.constant.TransactionType;
import com.java.banksystemproject.util.impl.JDBCUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class TransactionDaoJDBC implements ITransactionDao {
    private final DataSource dataSource = JDBCUtil.createDataSource();

    @Override
    public Optional<Transaction> get(long id) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM TRANSACTIONS WHERE TRANSACTION_ID=?")) {
                ps.setLong(1, id);

                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    return Optional.of(Transaction.builder()
                            .transactionId(resultSet.getLong("TRANSACTION_ID"))
                            .transactionType(TransactionType.valueOf(resultSet.getString("TRANSACTION_TYPE")))
                            .transactionDate(resultSet.getDate("TRANSACTION_DATE"))
                            .sourceAccountNumber(resultSet.getString("SOURCE_ACCOUNT_NUMBER"))
                            .targetAccountNumber(resultSet.getString("TARGET_ACCOUNT_NUMBER"))
                            .amount(resultSet.getDouble("AMOUNT"))
                            .fee(resultSet.getDouble("FEE"))
                            .status(TransactionStatus.valueOf(resultSet.getString("STATUS")))
                            .build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Transaction> getAll() {
        Collection<Transaction> collection = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM TRANSACTIONS")) {
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    Transaction transaction = Transaction.builder()
                            .transactionId(resultSet.getLong("TRANSACTION_ID"))
                            .transactionType(TransactionType.valueOf(resultSet.getString("TRANSACTION_TYPE")))
                            .transactionDate(resultSet.getDate("TRANSACTION_DATE"))
                            .sourceAccountNumber(resultSet.getString("SOURCE_ACCOUNT_NUMBER"))
                            .targetAccountNumber(resultSet.getString("TARGET_ACCOUNT_NUMBER"))
                            .amount(resultSet.getDouble("AMOUNT"))
                            .fee(resultSet.getDouble("FEE"))
                            .status(TransactionStatus.valueOf(resultSet.getString("STATUS")))
                            .build();

                    collection.add(transaction);
                }
            }

            return collection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Transaction transaction) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO TRANSACTIONS(TRANSACTION_ID, TRANSACTION_TYPE, AMOUNT, TRANSACTION_DATE, SOURCE_ACCOUNT_NUMBER, TARGET_ACCOUNT_NUMBER, STATUS, FEE) VALUES(?, ?, ?, ?, ?, ?, ?, ?)")) {
                ps.setLong(1, transaction.getTransactionId());
                ps.setString(2, transaction.getTransactionType().name());
                ps.setDouble(3, transaction.getAmount());
                java.util.Date utilDate = transaction.getTransactionDate();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                ps.setDate(4, sqlDate);
//                ps.setDate(4, (java.sql.Date) transaction.getTransactionDate());
                ps.setString(5, transaction.getSourceAccountNumber());
                ps.setString(6, transaction.getTargetAccountNumber());
                ps.setString(7, transaction.getStatus().name());
                ps.setDouble(8, transaction.getFee());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Transaction transaction) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM TRANSACTIONS WHERE TRANSACTION_ID = ?")) {
                ps.setLong(1, transaction.getTransactionId());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
