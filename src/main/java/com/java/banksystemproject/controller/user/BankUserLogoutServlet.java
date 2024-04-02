package com.java.banksystemproject.controller.user;

import com.java.banksystemproject.dao.impl.JDBC.BankUserDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.TokenDaoJDBC;
import com.java.banksystemproject.service.impl.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "BankUserLogutServlet", value = "/logout")

public class BankUserLogoutServlet extends HttpServlet {
    private final AuthenticationService authenticationService = new AuthenticationService(new BankUserDaoJDBC(), new TokenDaoJDBC());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            authenticationService.logout(request, response);
            HttpSession session = request.getSession(false); // create session if it doesn't exist
            session.setAttribute("user", null);

            request.setAttribute("messageType", "success");
            request.setAttribute("messageText", "Logout Was Successful!");

            response.sendRedirect(request.getContextPath() + "/index.html");
        }
        catch (IllegalArgumentException e){
            request.setAttribute("messageType", "error");
            request.setAttribute("messageText", "Logout Was Not Successful!");
        }
    }
}
