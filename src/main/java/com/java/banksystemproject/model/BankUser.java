package com.java.banksystemproject.model;

import javax.persistence.*;

import com.java.banksystemproject.model.account.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BankUser implements Serializable {
    @Id
    private String userName;
    private String passWord;//Hash Value
    @Column(name = "is_admin")
    private boolean isAdmin;

    @ManyToMany
    @JoinTable(name = "bankuser_bankaccount",
            joinColumns = @JoinColumn(name = "bank_user_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_account_id"))
    private Set<BankAccount> bankAccounts = new HashSet<>();

}
