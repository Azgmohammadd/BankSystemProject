package com.java.banksystemproject.model.account;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "bank_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BankAccount implements Serializable {
    @Id
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_holder_number")
    private String accountHolderNumber;
    @Column(name = "balance")
    private double balance;

    public BankAccount(String accountNumber, String accountHolderNumber) {
        this.setAccountNumber(accountNumber);
        this.setAccountHolderNumber(accountHolderNumber);
        this.setBalance(0);
    }
}
