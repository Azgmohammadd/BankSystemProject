package com.java.banksystemproject.model.account;

import javax.persistence.*;

import com.java.banksystemproject.model.BankUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public class BankAccount implements Serializable {
    @Id
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_holder_number")
    private String accountHolderNumber;
    @Column(name = "balance")
    private double balance;
    @Column(name = "account_type", insertable = false, updatable = false)
    private String type;
    @Column(name = "is_active")
    @Builder.Default
    private boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "bankuser_bankaccount",
            joinColumns = @JoinColumn(name = "bank_account_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_user_id",
                    nullable = false))
    private Set<BankUser> bankUsers = new HashSet<>();

    public BankAccount(String accountNumber, String accountHolderNumber) {
        this.setAccountNumber(accountNumber);
        this.setAccountHolderNumber(accountHolderNumber);
        this.setBalance(0);
        this.setActive(true);
    }

    public BankAccount(String accountNumber, String accountHolderNumber, double initialAmount) {
        this.setAccountNumber(accountNumber);
        this.setAccountHolderNumber(accountHolderNumber);
        this.setBalance(initialAmount);
        this.setActive(true);

    }

}
