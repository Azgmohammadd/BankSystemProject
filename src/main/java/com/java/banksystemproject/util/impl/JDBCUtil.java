package com.java.banksystemproject.util.impl;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class JDBCUtil {
    private final static String url;
    private final static String driverName;
    private final static String username;
    private final static String password;

    static {
        Properties properties = PropertiesFileUtil.getProperties("src/main/resources/application.properties");
        url = properties.getProperty("datasource.url");
        driverName = properties.getProperty("datasource.driverName");
        username = properties.getProperty("datasource.username", null);
        password = properties.getProperty("datasource.password", null);
    }

    public static DataSource createDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
//        ds.setUsername(username);
//        ds.setPassword(password);

        return ds;
    }
}
