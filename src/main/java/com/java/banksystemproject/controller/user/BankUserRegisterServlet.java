package com.java.banksystemproject.controller.user;

import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.service.factory.BankUserServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BankUserRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BankUser user = BankUser.builder()
                .userName(request.getParameter("username"))
                .passWord(request.getParameter("password"))
                .isAdmin("true".equals(request.getParameter("isAdmin")))
                .build();
        new BankUserServiceFactory().get().register(user);

        String jsonResponse = "{\"message\":\"Registering user was successful!\"}";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);

    }

}
