package com.java.banksystemproject.controller.user;

import com.java.banksystemproject.service.factory.BankUserServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BankUserRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new BankUserServiceFactory().get().register(request.getParameter("username"),request.getParameter("password"));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\":\"Registering user was successful!\"}");

    }

}
