package com.java.banksystemproject.dao.impl.JDBC;

import com.java.banksystemproject.dao.ITokenDao;
import com.java.banksystemproject.model.Token;
import com.java.banksystemproject.util.impl.JDBCUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TokenDaoJDBC implements ITokenDao {
    private final DataSource dataSource = JDBCUtil.createDataSource();

    @Override
    public List<Token> findAllValidTokenByUser(Integer id) {
        List<Token> list = new ArrayList<>();

        try(Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select * from Token t inner join bank_user u on t.USER_ID = u.USERNAME where u.USERNAME= ? and (t.expired=false or t.revoked=false)")) {
                ps.setInt(1, id);

                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    Token token = Token.builder()
                            .id(resultSet.getInt("id"))
                            .token(resultSet.getString("token"))
                            .revoked(resultSet.getBoolean("revoked"))
                            .expired(resultSet.getBoolean("expired"))
                            .build();

                    list.add(token);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Token> findByToken(String tokenString) {
        try(Connection conn = dataSource.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM Token t WHERE t.token = ?")) {
                ps.setString(1, tokenString);

                ResultSet resultSet = ps.executeQuery();

                if (resultSet.next()) {
                    Token token = Token.builder()
                            .id(resultSet.getInt("id"))
                            .token(resultSet.getString("token"))
                            .revoked(resultSet.getBoolean("revoked"))
                            .expired(resultSet.getBoolean("expired"))
                            .build();

                    return Optional.of(token);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public void save(Token storedToken) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("insert into TOKEN(TOKEN_ID, TOKEN, TOKEN_TYPE, REVOKED, EXPIRED) VALUES ( ?, ?, ?, ?, ?)")) {
                ps.setInt(1, storedToken.getId());
                ps.setString(2, storedToken.getToken());
                ps.setString(3, storedToken.getTokenType().name());
                ps.setBoolean(4, storedToken.isRevoked());
                ps.setBoolean(5, storedToken.isExpired());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Token storedToken) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE TOKEN SET EXPIRED =?, REVOKED = ? WHERE USER_ID =?")) {
                ps.setBoolean(1, storedToken.isExpired());
                ps.setBoolean(2, storedToken.isRevoked());
                ps.setInt(3, storedToken.getId());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
