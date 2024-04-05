package com.java.banksystemproject.controller.user;

import com.java.banksystemproject.model.authentication.AuthenticationRequest;
import com.java.banksystemproject.model.authentication.AuthenticationResponse;
import com.java.banksystemproject.service.IAuthenticationService;
import com.java.banksystemproject.service.factory.AuthenticationServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "BankUserLoginServlet", value = "/login")
public class BankUserLoginServlet extends HttpServlet {
    private final IAuthenticationService authenticationService = new AuthenticationServiceFactory().getJDBC();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthenticationRequest user =AuthenticationRequest.builder()
                .username(request.getParameter("username"))
                .password(request.getParameter("password"))
                .build();

        try {
            AuthenticationResponse authResponse = authenticationService.authenticate(user);
            HttpSession session = request.getSession(true); // create session if it doesn't exist
            session.setAttribute("user", user);

            request.setAttribute("messageType", "success");
            request.setAttribute("messageText", "Login Was Successful!");
            request.setAttribute("token", authResponse.getAccessToken());
            response.sendRedirect(request.getContextPath() + "/user?username=" + user.getUsername());
        }
        catch (IllegalArgumentException e){
            request.setAttribute("messageType", "error");
            request.setAttribute("messageText", "Login Was Not Successful!");
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }

}