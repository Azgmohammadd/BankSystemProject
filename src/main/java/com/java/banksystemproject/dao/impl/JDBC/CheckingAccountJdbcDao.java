package com.java.banksystemproject.dao.impl.JDBC;

import com.java.banksystemproject.dao.Dao;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.util.impl.JDBCUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CheckingAccountJdbcDao implements Dao<CheckingAccount> {
    private final DataSource dataSource = JDBCUtil.createDataSource();

    @Override
    public Optional<CheckingAccount> get(String id) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM CHECKING_ACCOUNT WHERE ACCOUNT_NUMBER=?")) {
                ps.setString(1, id);

                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    return Optional.ofNullable(
                            CheckingAccount.builder()
                                    .accountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                                    .accountHolderNumber(resultSet.getString("ACCOUNT_HOLDER_NUMBER"))
                                    .balance(resultSet.getDouble("BALANCE"))
                                    .overdraftLimit(resultSet.getDouble("OVER_DRAFT_LIMIT"))
                                    .build()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<CheckingAccount> getAll() {
        Collection<CheckingAccount> collection = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM CHECKING_ACCOUNT")) {
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    CheckingAccount checkingAccount = CheckingAccount.builder()
                            .accountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                            .accountHolderNumber(resultSet.getString("ACCOUNT_HOLDER_NUMBER"))
                            .balance(resultSet.getDouble("BALANCE"))
                            .overdraftLimit(resultSet.getDouble("OVER_DRAFT_LIMIT"))
                            .build();

                    collection.add(checkingAccount);
                }
            }
            return collection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(CheckingAccount account) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO CHECKING_ACCOUNT (ACCOUNT_NUMBER, ACCOUNT_HOLDER_NUMBER, BALANCE, OVER_DRAFT_LIMIT) VALUES ( ?, ?, ?, ? )")) {
                ps.setString(1, account.getAccountNumber());
                ps.setString(2, account.getAccountHolderNumber());
                ps.setDouble(3, account.getBalance());
                ps.setDouble(4, account.getOverdraftLimit());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(CheckingAccount account) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM CHECKING_ACCOUNT WHERE ACCOUNT_NUMBER = ?")) {
                ps.setString(1, account.getAccountNumber());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}