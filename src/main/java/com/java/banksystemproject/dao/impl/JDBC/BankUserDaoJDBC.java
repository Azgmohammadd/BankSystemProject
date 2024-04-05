package com.java.banksystemproject.dao.impl.JDBC;

import com.java.banksystemproject.dao.IBankUserDao;
import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.constant.Role;
import com.java.banksystemproject.util.impl.JDBCUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class BankUserDaoJDBC implements IBankUserDao {
    private final DataSource dataSource = JDBCUtil.createDataSource();

    @Override
    public List<BankUser> getAll() {
        try(Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM BANK_USER")) {
                Map<String, BankUser> map = new HashMap<>();

                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    BankUser user = BankUser.builder()
                            .firstName(resultSet.getString("firstName"))
                            .lastName(resultSet.getString("lastName"))
                            .username(resultSet.getString("username"))
                            .nationalCode(resultSet.getString("national_Code"))
                            .role(Role.valueOf(resultSet.getString("role")))
                            .bankAccountsId(new HashSet<>())
                            .build();

                    String bankAccountIdString = resultSet.getString("BANK_ACCOUNT_ID") == null ? "" :resultSet.getString("BANK_ACCOUNT_ID");

                    if (map.containsKey(user.getUsername())) {
                        BankUser _user = map.get(user.getUsername());
                        Set<String> bankAccountsId = new HashSet<>(_user.getBankAccountsId());
                        bankAccountsId.add(bankAccountIdString);
                        _user.setBankAccountsId(bankAccountsId);
                        map.put(_user.getUsername(), _user);
                    } else {
                        user.setBankAccountsId(Set.of(bankAccountIdString));
                        map.put(user.getUsername(), user);
                    }
                }

                return map.values().stream().toList();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BankUser> get(String id) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select * from BANK_USER where USERNAME = ?")) {
                ps.setString(1, id);

                ResultSet resultSet = ps.executeQuery();

                BankUser user = null;

                Set<String> bankAccountsId = new HashSet<>();

                while (resultSet.next()) {
                    user = BankUser.builder()
                            .username(resultSet.getString("username"))
                            .password(resultSet.getString("password"))
                            .role(Role.valueOf(resultSet.getString("role")))
                            .firstName(resultSet.getString("firstName"))
                            .lastName(resultSet.getString("lastName"))
                            .nationalCode(resultSet.getString("national_Code"))
                            .build();
                    bankAccountsId.add(resultSet.getString("BANK_ACCOUNT_ID"));
                }

                if (user != null) {
                    user.setBankAccountsId(bankAccountsId);
                }

                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(BankUser user) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("insert into BANK_USER (USERNAME, PASSWORD, ROLE, FIRSTNAME, LASTNAME, NATIONAL_CODE, BANK_ACCOUNT_ID) values (?, ?, ?, ?, ?, ?, ?);")) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                String role = user.getRole().toString();
                ps.setString(3, role);
                ps.setString(4, user.getFirstName());
                ps.setString(5, user.getLastName());
                ps.setString(6, user.getNationalCode());

                if (user.getBankAccountsId() == null || user.getBankAccountsId().isEmpty()) {
                    ps.setNull(7, Types.VARCHAR);
                    ps.executeUpdate();
                }else {
                    for (String accString : user.getBankAccountsId()) {
                        ps.setString(7, accString);

                    }
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addAccount(BankUser bankUser, BankAccount bankAccount) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE BANK_USER SET BANK_ACCOUNT_ID = ? WHERE USERNAME = ?")) { //incorrect
                ps.setString(1, bankAccount.getAccountNumber());

                ps.setString(2, bankUser.getUsername());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
