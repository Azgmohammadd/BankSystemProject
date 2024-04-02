package com.java.banksystemproject.dao.impl.JDBC;

import com.java.banksystemproject.dao.IBankUserDao;
import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.constant.Role;
import com.java.banksystemproject.util.impl.JDBCUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class BankUserDaoJDBC implements IBankUserDao {
    private final DataSource dataSource = JDBCUtil.createDataSource();

    @Override
    public Optional<BankUser> get(String id) {
        try(Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select * from BANK_USER bu where bu.USERNAME = ?")) {
                ps.setString(1, id);

                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    BankUser user = BankUser.builder()
                            .username(resultSet.getString("username"))
                            .password(resultSet.getString("password"))
                            .role(Role.valueOf(resultSet.getString("role")))
                            .build();

                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void save(BankUser user) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("insert into BANK_USER (USERNAME, PASSWORD, ROLE) values (?, ?, ?);")) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                String role = user.getRole().toString();
                ps.setString(3, role);

                ps.executeUpdate();
            }
           }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
