package com.java.banksystemproject.model.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BankAccount implements Serializable {
    private String accountNumber;
    private String accountHolderNumber;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderNumber) {
        this.setAccountNumber(accountNumber);
        this.setAccountHolderNumber(accountHolderNumber);
        this.setBalance(0);
    }
}
