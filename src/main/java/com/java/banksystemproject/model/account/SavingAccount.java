package com.java.banksystemproject.model.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@DiscriminatorValue("Saving")
public class SavingAccount extends BankAccount implements Serializable {
    @Column(name = "monthly_interest_rate")
    private double MonthlyInterestRate;

    @Column(name = "minimum_balance")
    private double MINIMUM_BALANCE;

    @Column(name = "minimum_balance_in_month")
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

