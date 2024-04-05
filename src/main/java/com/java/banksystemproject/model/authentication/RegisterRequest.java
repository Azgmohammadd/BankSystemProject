package com.java.banksystemproject.model.authentication;

import com.java.banksystemproject.model.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private String nationalCode;
}
