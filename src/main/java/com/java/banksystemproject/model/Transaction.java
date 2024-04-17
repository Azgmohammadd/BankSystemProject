package com.java.banksystemproject.model;

import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.model.constant.TransactionType;
import com.java.banksystemproject.util.impl.TransactionIdGenerator;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements Serializable {
    @Builder.Default
    private long transactionId = TransactionIdGenerator.generate();
    private TransactionType transactionType;
    private double amount;
    private Date transactionDate;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    @Builder.Default
    private TransactionStatus status = TransactionStatus.PENDING;
    private double fee;
}
