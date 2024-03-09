package com.java.banksystemproject.util.impl;

import com.java.banksystemproject.util.IFileService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileService {
    public Properties readPropertiesFile() {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop;
    }
}
