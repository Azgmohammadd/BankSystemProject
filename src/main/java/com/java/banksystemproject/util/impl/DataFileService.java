package com.java.banksystemproject.util.impl;

import com.java.banksystemproject.entities.BankAccount;
import com.java.banksystemproject.util.IFileService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataFileService implements IFileService<String, BankAccount> {
    @Override
    public void writeToFile(String filePath, Map<String, BankAccount> bankAccountList) throws IOException {

        File file = new File(filePath);
        try (
                FileOutputStream outputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ) {
            objectOutputStream.writeObject(bankAccountList);
        }
    }

    @Override
    public Map<String, BankAccount> readFromFile(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);

        try (
                FileInputStream fileReader = new FileInputStream(file);
                ObjectInputStream objectStream = new ObjectInputStream(fileReader);
        ) {
            return (HashMap<String, BankAccount>) objectStream.readObject();
        }
    }

}
