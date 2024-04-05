package com.java.banksystemproject.model;

import javax.persistence.*;

import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private String username;

    private String password;//Hash Value

    private String firstName;

    private String lastName;

    private String nationalCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role =Role.USER;

    @ManyToMany
    @JoinTable(name = "bankuser_bankaccount",
            joinColumns = @JoinColumn(name = "bank_user_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_account_id"))
    private Set<String> bankAccountsId = new HashSet<>();
}
