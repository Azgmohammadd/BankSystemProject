package com.java.banksystemproject.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.FeeDetail;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionType;
import com.java.banksystemproject.service.ITransactionService;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TransactionService implements ITransactionService {
    public static Map<TransactionType, FeeDetail> readFeeDetailsFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, Double>> jsonMap = mapper.readValue(new File(""), new TypeReference<Map<String, Map<String, Double>>>(){});
        Map<TransactionType, FeeDetail> feeDetailMap = new HashMap<>();

        for (Map.Entry<String, Map<String, Double>> entry : jsonMap.entrySet()) {
            TransactionType transactionType = TransactionType.valueOf(entry.getKey());
            Map<String, Double> detailValues = entry.getValue();
            double fee = detailValues.get("fee");
            double ceilAmount = detailValues.get("ceilAmount");
            double floorAmount = detailValues.get("floorAmount");
            feeDetailMap.put(transactionType, new FeeDetail(fee, ceilAmount, floorAmount));
        }

        return feeDetailMap;
    }

    private double deductFees(BankAccount account, double amount, TransactionType transactionType) {
        if (account instanceof CheckingAccount) {
            FeeDetail feeDetail = FeeDetail.feeDetailMap.get(transactionType);

            double fee = (1 + feeDetail.getFee()) * amount;

            if (fee > feeDetail.getCeilAmount()) {
                fee = feeDetail.getCeilAmount();
            } else if (fee < feeDetail.getFloorAmount()) {
                fee = feeDetail.getFloorAmount();
            }

            return fee;
        }
        return 0;
    }

    @Override
    public Transaction createWithdrawTransaction(BankAccount account, double amount) {
        return Transaction.builder()
                .transactionType(TransactionType.WITHDRAWALS)
                .transactionDate(new Date())
                .amount(amount)
                .sourceAccount(account)
                .fee(deductFees(account, amount, TransactionType.WITHDRAWALS))
                .build();
    }

    @Override
    public Transaction createDepositTransaction(BankAccount account, double amount) {
        return Transaction.builder()
                .transactionType(TransactionType.DEPOSITS)
                .transactionDate(new Date())
                .amount(amount)
                .sourceAccount(account)
                .fee(deductFees(account, amount, TransactionType.DEPOSITS))
                .build();
    }

    @Override
    public Transaction createGetBalanceTransaction(BankAccount account) {
        return Transaction.builder()
                .transactionType(TransactionType.GET_BALANCE)
                .transactionDate(new Date())
                .sourceAccount(account)
                .fee(deductFees(account, 0, TransactionType.GET_BALANCE))
                .build();
    }

}

