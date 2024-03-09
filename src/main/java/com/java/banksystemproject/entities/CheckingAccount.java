package com.java.banksystemproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CheckingAccount extends BankAccount implements Serializable {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolderNumber, double overdraftLimit) {
        super(accountNumber, accountHolderNumber);
        this.setOverdraftLimit(overdraftLimit);
    }

    public CheckingAccount(String accountNumber, String accountHolderNumber, double initialAmount, double overdraftLimit) {
        super(accountNumber, accountHolderNumber, initialAmount);
        this.setOverdraftLimit(overdraftLimit);
    }
}

