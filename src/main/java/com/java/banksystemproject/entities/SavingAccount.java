package com.java.banksystemproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SavingAccount extends BankAccount implements Serializable {
    private float MonthlyInterestRate;
    private double MINIMUM_BALANCE;
    private double minimumBalanceInMonth;

    public SavingAccount(String accountNumber, String accountHolderNumber, float interestRate) {
        super(accountNumber, accountHolderNumber);
        this.setMonthlyInterestRate(interestRate);
        this.setMinimumBalanceInMonth(this.getBalance());
    }

    public SavingAccount(String accountNumber, String accountHolderNumber, double initialAmount, float interestRate) {
        super(accountNumber, accountHolderNumber, initialAmount);
        this.setMonthlyInterestRate(interestRate);
        this.setMinimumBalanceInMonth(this.getBalance());
    }
}

