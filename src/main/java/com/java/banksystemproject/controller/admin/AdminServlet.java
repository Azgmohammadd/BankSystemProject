package com.java.banksystemproject.controller.admin;

import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.service.IBankUserService;
import com.java.banksystemproject.service.factory.BankUserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    IBankUserService bankUserService = new BankUserServiceFactory().get();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BankUser> users = bankUserService.getAllUsers();

        request.setAttribute("users", users);
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }
}
