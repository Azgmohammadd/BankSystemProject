package com.java.banksystemproject.model;

import com.java.banksystemproject.model.constant.TokenType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tokens")
@Data
@Builder
public class Token implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "token_id")
    public Integer id;

    @Column(name = "token", unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "token_type")
    public TokenType tokenType = TokenType.BEARER;

    @Column(name = "revoked")
    public boolean revoked;

    @Column(name = "expired")
    public boolean expired;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    public BankUser user;
    // username is in token body
}
