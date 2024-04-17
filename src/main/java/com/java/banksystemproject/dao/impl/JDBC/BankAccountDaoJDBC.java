package com.java.banksystemproject.dao.impl.JDBC;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.util.impl.JDBCUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BankAccountDaoJDBC implements IBankAccountDao {
    private final DataSource dataSource = JDBCUtil.createDataSource();

    @Override
    public Optional<BankAccount> get(String id) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM ACCOUNTS WHERE ACCOUNT_NUMBER=?")) {
                ps.setString(1, id);

                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    if (resultSet.getString("account_type").equals("Saving")) {
                        return Optional.ofNullable(SavingAccount.builder()
                                .accountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                                .accountHolderNumber(resultSet.getString("ACCOUNT_HOLDER_NUMBER"))
                                .balance(resultSet.getDouble("BALANCE"))
                                .monthlyInterestRate(resultSet.getFloat("MONTHLY_INTEREST_RATE"))
                                .minimumBalance(resultSet.getDouble("MINIMUM_BALANCE"))
                                .minimumBalanceInMonth(resultSet.getDouble("MINIMUM_BAlANCE_IN_MONTH"))
                                .type("Saving")
                                .active(resultSet.getBoolean("IS_ACTIVE"))
                                .build());
                    } else if (resultSet.getString("account_type").equals("Checking")) {
                        return Optional.ofNullable(CheckingAccount.builder()
                                .accountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                                .accountHolderNumber(resultSet.getString("ACCOUNT_HOLDER_NUMBER"))
                                .balance(resultSet.getDouble("BALANCE"))
                                .overdraftLimit(resultSet.getDouble("OVERDRAFT_LIMIT"))
                                .type("Checking")
                                .active(resultSet.getBoolean("IS_ACTIVE"))
                                .build());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<BankAccount> getAll() {
        Collection<BankAccount> collection = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM ACCOUNTS")) {
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    if (resultSet.getString("account_type").equals("Saving")) {
                        SavingAccount savingAccount = SavingAccount.builder()
                                .accountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                                .accountHolderNumber(resultSet.getString("ACCOUNT_HOLDER_NUMBER"))
                                .balance(resultSet.getDouble("BALANCE"))
                                .monthlyInterestRate(resultSet.getFloat("MONTHLY_INTEREST_RATE"))
                                .minimumBalance(resultSet.getDouble("MINIMUM_BALANCE"))
                                .minimumBalanceInMonth(resultSet.getDouble("MINIMUM_BAlANCE_IN_MONTH"))
                                .type("Saving")
                                .active(resultSet.getBoolean("IS_ACTIVE"))
                                .build();

                        collection.add(savingAccount);
                    } else if (resultSet.getString("account_type").equals("Checking")) {
                        CheckingAccount checkingAccount = CheckingAccount.builder()
                                .accountNumber(resultSet.getString("ACCOUNT_NUMBER"))
                                .accountHolderNumber(resultSet.getString("ACCOUNT_HOLDER_NUMBER"))
                                .balance(resultSet.getDouble("BALANCE"))
                                .overdraftLimit(resultSet.getDouble("OVERDRAFT_LIMIT"))
                                .type("Checking")
                                .active(resultSet.getBoolean("IS_ACTIVE"))
                                .build();

                        collection.add(checkingAccount);
                    }
                }
            }
            return collection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(BankAccount account) {

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO ACCOUNTS(ACCOUNT_TYPE, ACCOUNT_NUMBER, ACCOUNT_HOLDER_NUMBER, BALANCE, OVERDRAFT_LIMIT, MINIMUM_BALANCE, MONTHLY_INTEREST_RATE, MINIMUM_BALANCE_IN_MONTH, IS_ACTIVE) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                ps.setString(2, account.getAccountNumber());
                ps.setString(3, account.getAccountHolderNumber());
                ps.setDouble(4, account.getBalance());

                if (account instanceof SavingAccount savingAccount) {
                    ps.setString(1, "Saving");
                    ps.setNull(5, Types.DOUBLE);
                    ps.setDouble(6, savingAccount.getMinimumBalance());
                    ps.setDouble(7, savingAccount.getMonthlyInterestRate());
                    ps.setDouble(8, savingAccount.getMinimumBalanceInMonth());
                    ps.setBoolean(9, savingAccount.isActive());
                } else if (account instanceof CheckingAccount checkingAccount) {
                    ps.setString(1, "Checking");
                    ps.setDouble(5, checkingAccount.getOverdraftLimit());
                    ps.setNull(6, Types.DOUBLE);
                    ps.setNull(7, Types.FLOAT);
                    ps.setNull(8, Types.DOUBLE);
                    ps.setBoolean(9, checkingAccount.isActive());
                }

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(BankAccount account) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM ACCOUNTS WHERE ACCOUNT_NUMBER = ?")) {
                ps.setString(1, account.getAccountNumber());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBalance(BankAccount account, double balance) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE ACCOUNTS SET BALANCE = ? WHERE ACCOUNT_NUMBER = ?")) {
                ps.setDouble(1, balance);
                ps.setString(2, account.getAccountNumber());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMinimumBalance(SavingAccount account, double balance) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE ACCOUNTS SET MINIMUM_BALANCE = ? WHERE ACCOUNT_NUMBER = ?")) {
                ps.setDouble(1, balance);
                ps.setString(2, account.getAccountNumber());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
