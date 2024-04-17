package com.java.banksystemproject.service.impl;

import com.java.banksystemproject.dao.IBankUserDao;
import com.java.banksystemproject.dao.ITokenDao;
import com.java.banksystemproject.model.authentication.AuthenticationRequest;
import com.java.banksystemproject.model.authentication.AuthenticationResponse;
import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.authentication.RegisterRequest;
import com.java.banksystemproject.service.IAuthenticationService;
import com.java.banksystemproject.util.impl.JwtUtil;
import com.java.banksystemproject.util.impl.PasswordEncoder;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final IBankUserDao userDao;
    private final ITokenDao tokenDao;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = BankUser.builder()
                .username(request.getUsername())
                .password(PasswordEncoder.encodePassword(request.getPassword()))
                .role(request.getRole())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .nationalCode(request.getNationalCode())
                .build();

        userDao.save(user);

        String accessToken = JwtUtil.generateToken(user);
        String refreshToken = JwtUtil.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .responseMessage("registered")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        BankUser user = userDao.get(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found")
        );

        if (!PasswordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String accessToken = JwtUtil.generateToken(user);
        String refreshToken = JwtUtil.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .responseMessage("authenticated")
                .role(user.getRole().name())
                .build();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7);
        var stored_token = tokenDao.findByToken(jwt).orElse(null);

        if (stored_token != null) {
            stored_token.setExpired(true);
            stored_token.setRevoked(true);

            tokenDao.save(stored_token);
        }
    }
}
