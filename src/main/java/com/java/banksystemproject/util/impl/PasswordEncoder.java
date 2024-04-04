package com.java.banksystemproject.util.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncoder {
    public static String encodePassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes());

            byte[] hashedBytes = md.digest();

            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean matches(String plainPassword, String hashedPassword) {
        String hashedPlainPassword = encodePassword(plainPassword);
        return hashedPassword.equals(hashedPlainPassword);
    }
}

