package com.java.banksystemproject.controller.user;

import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.service.IBankUserService;
import com.java.banksystemproject.service.factory.BankUserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    IBankUserService bankUserService = new BankUserServiceFactory().get();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        request.setAttribute("username", username);

        BankUser bankUser = bankUserService.get(username);
        request.setAttribute("user", bankUser);

        List<BankAccount> accounts = bankUserService.getAllAccounts(bankUser);
        request.setAttribute("accounts", accounts);

        request.setAttribute("isUser", bankUser.getRole().name().equals("USER"));

        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }
}
