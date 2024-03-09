package com.java.banksystemproject.services.exception;

public class InvalidTransactionException extends RuntimeException{
    public InvalidTransactionException(String message) {
        super(message);
    }
}
