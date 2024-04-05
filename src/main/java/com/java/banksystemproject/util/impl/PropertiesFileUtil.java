package com.java.banksystemproject.util.impl;

import com.java.banksystemproject.service.account.impl.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    public static Properties getProperties(String filePath) {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream(filePath)) {
            prop.load(input);

        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }

        return prop;
    }
}
