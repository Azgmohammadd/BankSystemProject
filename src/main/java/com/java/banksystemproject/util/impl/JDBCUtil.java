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
        String path = System.getProperty("user.dir") + "/src/main/resources/application.properties"; //incorrect
        Properties properties = PropertiesFileUtil.getProperties(path);
        url = properties.getProperty("datasource.url");
        driverName = properties.getProperty("datasource.driverName");
        username = properties.getProperty("datasource.username", null);
        password = properties.getProperty("datasource.password", null);
    }

    public static DataSource createDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:default;INIT=RUNSCRIPT FROM 'classpath:scheme.sql';"); //incorrect way
        ds.setDriverClassName("org.h2.Driver");

        return ds;
    }
}
