package com.java.banksystemproject.model;

import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.model.constant.TransactionType;
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
    private String sourceAccountNumber;
    private String targetAccountNumber;
    @Builder.Default
    private TransactionStatus status = TransactionStatus.PENDING;
    private double fee;
}
