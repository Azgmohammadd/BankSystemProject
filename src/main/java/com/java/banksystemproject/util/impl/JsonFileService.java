package com.java.banksystemproject.util.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.util.IFileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonFileService implements IFileService<String, BankAccount> {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void writeToFile(String filePath, Map<String, BankAccount> bankAccountMap) throws IOException {
        File file = new File(filePath);

        try (FileOutputStream fileOutput = new FileOutputStream(file)) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileOutput, bankAccountMap);
        }
    }

    @Override
    public Map<String, BankAccount> readFromFile(String filePath) throws IOException {
        File file = new File(filePath);

        try (FileInputStream fileInput = new FileInputStream(file)) {
            TypeReference<HashMap<String, BankAccount>> mapType = new TypeReference<HashMap<String, BankAccount>>() {};

            return mapper.readValue(fileInput, mapType);
        }
    }
}