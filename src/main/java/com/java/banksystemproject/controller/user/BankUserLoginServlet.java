package com.java.banksystemproject.controller.user;

import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.service.factory.BankUserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BankUserLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BankUser user = BankUser.builder()
                .userName(request.getParameter("username"))
                .passWord(request.getParameter("password"))
                .build();

        try {
            BankUser authUser = new BankUserServiceFactory().get().authenticate(user);
            request.setAttribute("messageType", "success");
            request.setAttribute("messageText", "Login Was Successful!");
        }
        catch (IllegalArgumentException e){
            request.setAttribute("messageType", "error");
            request.setAttribute("messageText", "Login Was Not Successful!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}