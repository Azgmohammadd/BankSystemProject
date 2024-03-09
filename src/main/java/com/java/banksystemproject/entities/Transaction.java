package com.java.banksystemproject.entities;

import com.java.banksystemproject.entities.constants.TransactionStatus;
import com.java.banksystemproject.entities.constants.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements Serializable {
    private long transactionId;
    private TransactionType transactionType;
    private double amount;
    private Date transactionDate;
    private BankAccount sourceAccount;
    private BankAccount targetAccount;
    @Builder.Default
    private TransactionStatus status = TransactionStatus.PENDING;
    private double fee;
}
