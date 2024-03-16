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
        BankUser user = new BankUserServiceFactory().get()
                .authenticate(request.getParameter("username"), request.getParameter("password"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\":\"Log In was successful!\"}");

    }

}