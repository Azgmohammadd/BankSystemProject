package com.java.banksystemproject.util.impl;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    public static String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean matches(String plainPassword, String hashedPassword) {
        String hashedPlainPassword = encodePassword(plainPassword);
        return hashedPassword.equals(hashedPlainPassword);
    }
}

