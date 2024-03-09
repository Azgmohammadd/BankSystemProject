package com.java.banksystemproject.entities;

import com.java.banksystemproject.entities.constants.TransactionType;
import com.java.banksystemproject.services.impl.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Map;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class FeeDetail {
    private final double fee;
    private final double ceilAmount;
    private final double floorAmount;
    public final static Map<TransactionType, FeeDetail> feeDetailMap;

    static {
        try {
            feeDetailMap =  TransactionService.readFeeDetailsFromJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

