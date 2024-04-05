package com.java.banksystemproject.util.impl;

import com.java.banksystemproject.service.account.impl.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncoder {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountService.class);

    public static String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes());

            byte[] hashedBytes = md.digest();

            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    public static boolean matches(String plainPassword, String hashedPassword) {
        String hashedPlainPassword = encodePassword(plainPassword);
        return hashedPassword.equals(hashedPlainPassword);
    }
}

