package com.java.banksystemproject.service;

import com.java.banksystemproject.model.authentication.AuthenticationRequest;
import com.java.banksystemproject.model.authentication.AuthenticationResponse;
import com.java.banksystemproject.model.authentication.RegisterRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse logout(HttpServletRequest request, HttpServletResponse response);
}
