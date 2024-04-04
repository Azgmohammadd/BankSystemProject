package com.java.banksystemproject.controller.user;

import com.java.banksystemproject.model.authentication.AuthenticationResponse;
import com.java.banksystemproject.model.authentication.RegisterRequest;
import com.java.banksystemproject.model.constant.Role;
import com.java.banksystemproject.service.IAuthenticationService;
import com.java.banksystemproject.service.factory.AuthenticationServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "BankUserRegisterServlet", value = "/register")
public class BankUserRegisterServlet extends HttpServlet {
    private final IAuthenticationService authenticationService = new AuthenticationServiceFactory().getJDBC();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegisterRequest user = RegisterRequest.builder()
                .username(request.getParameter("username"))
                .password(request.getParameter("password"))
                .role(Role.USER)
                .build();

        try {
            AuthenticationResponse authResponse = authenticationService.register(user);
            request.setAttribute("messageType", "success");
            request.setAttribute("messageText", "Register Was Successful!");
            request.setAttribute("authResponse", authResponse);
        } catch (IllegalArgumentException e) {
            request.setAttribute("messageType", "error");
            request.setAttribute("messageText", "Register Was Not Successful!: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/login");
    }

}
