package com.java.banksystemproject.util.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileUtil {
    public static Properties getProperties(String filePath) {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream(filePath)) {
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop;
    }
}
