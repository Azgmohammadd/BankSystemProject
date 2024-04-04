package com.java.banksystemproject.util.impl;

import java.util.UUID;

public class TransactionIdGenerator {
    public static long generate() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
