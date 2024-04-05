package com.java.banksystemproject.model.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue("Checking")
public class CheckingAccount extends BankAccount implements Serializable {
    @Column(name = "overdraft_limit")
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolderNumber, double overdraftLimit) {
        super(accountNumber, accountHolderNumber);
        this.setOverdraftLimit(overdraftLimit);
        this.setType("Checking");
    }

    public CheckingAccount(String accountNumber, String accountHolderNumber, double initialAmount, double overdraftLimit) {
        super(accountNumber, accountHolderNumber, initialAmount);
        this.setOverdraftLimit(overdraftLimit);
        this.setType("Checking");
    }
}


