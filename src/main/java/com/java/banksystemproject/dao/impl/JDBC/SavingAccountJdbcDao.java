package com.java.banksystemproject.dao.impl.JDBC;

import com.java.banksystemproject.dao.Dao;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.util.impl.JDBCUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class SavingAccountJdbcDao implements Dao<SavingAccount> {
    private final DataSource dataSource = JDBCUtil.createDataSource();
    @Override
    public Optional<SavingAccount> get(String id) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM SAVING_ACCOUNT WHERE ACCOUNT_NUMBER=?")) {
                ps.setString(1, id);

                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    return Optional.ofNullable(
                            SavingAccount.builder()
                                    .accountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                                    .accountHolderNumber(resultSet.getString("ACCOUNT_HOLDER_NUMBER"))
                                    .balance(resultSet.getDouble("BALANCE"))
                                    .MonthlyInterestRate(resultSet.getFloat("MONTHLY_INTEREST_RATE"))
                                    .MINIMUM_BALANCE(resultSet.getDouble("MINIMUM_BALANCE"))
                                    .minimumBalanceInMonth(resultSet.getDouble("MINIMUM_BAlANCE_IN_MONTH"))
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
    public Collection<SavingAccount> getAll() {
        Collection<SavingAccount> collection = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM SAVING_ACCOUNT")) {
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    SavingAccount savingAccount = SavingAccount.builder()
                            .accountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                            .accountHolderNumber(resultSet.getString("ACCOUNT_HOLDER_NUMBER"))
                            .balance(resultSet.getDouble("BALANCE"))
                            .MonthlyInterestRate(resultSet.getFloat("MONTHLY_INTEREST_RATE"))
                            .MINIMUM_BALANCE(resultSet.getDouble("MINIMUM_BALANCE"))
                            .minimumBalanceInMonth(resultSet.getDouble("MINIMUM_BAlANCE_IN_MONTH"))
                            .build();

                    collection.add(savingAccount);
                }
            }
            return collection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(SavingAccount account) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO SAVING_ACCOUNT(ACCOUNT_NUMBER, ACCOUNT_HOLDER_NUMBER, BALANCE, MONTHLY_INTEREST_RATE, MINIMUM_BALANCE, MINIMUM_BALANCE_IN_MONTH) VALUES ( ?, ?, ?, ?, ?, ? )")) {
                ps.setString(1, account.getAccountNumber());
                ps.setString(2, account.getAccountHolderNumber());
                ps.setDouble(3, account.getBalance());
                ps.setFloat(4, account.getMonthlyInterestRate());
                ps.setDouble(5, account.getMINIMUM_BALANCE());
                ps.setDouble(6, account.getMinimumBalanceInMonth());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SavingAccount account) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM SAVING_ACCOUNT WHERE ACCOUNT_NUMBER = ?")) {
                ps.setString(1, account.getAccountNumber());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
