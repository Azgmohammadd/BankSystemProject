package com.java.banksystemproject.controller.user;

import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.account.SavingAccount;
import com.java.banksystemproject.service.IBankUserService;
import com.java.banksystemproject.service.account.IBankAccountService;
import com.java.banksystemproject.service.account.factory.BankAccountServiceFactory;
import com.java.banksystemproject.service.factory.BankUserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/accounts", "/accounts/*"})
public class AccountServlet extends HttpServlet {
    private final IBankAccountService bankAccountService = new BankAccountServiceFactory().getJDBC();
    private final IBankUserService bankUserService = new BankUserServiceFactory().get();
    private String username;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        username = request.getParameter("username");

        request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        BankAccount bankAccount;

        switch (type) {
            case "Checking" -> {
                bankAccount = CheckingAccount.builder()
                        .accountNumber(request.getParameter("accountNumber"))
                        .accountHolderNumber(request.getParameter("accountHolderNumber"))
                        .balance(Double.parseDouble(request.getParameter("balance")))
                        .overdraftLimit(Double.parseDouble(request.getParameter("overdraftLimit")))
                        .type("Checking")
                        .build();
            }
            case "Saving" -> {
                bankAccount = SavingAccount.builder()
                        .accountNumber(request.getParameter("accountNumber"))
                        .accountHolderNumber(request.getParameter("accountHolderNumber"))
                        .balance(Double.parseDouble(request.getParameter("balance")))
                        .minimumBalance(Double.parseDouble(request.getParameter("minimumBalance")))
                        .minimumBalanceInMonth(Double.parseDouble(request.getParameter("minimumBalanceInMonth")))
                        .monthlyInterestRate(Double.parseDouble(request.getParameter("monthlyInterestRate")))
                        .type("Saving")
                        .build();
            }
            default -> {
                bankAccount = BankAccount.builder()
                        .accountNumber(request.getParameter("accountNumber"))
                        .accountHolderNumber(request.getParameter("accountHolderNumber"))
                        .balance(Double.parseDouble(request.getParameter("balance")))
                        .build();
            }
        }

        bankAccountService.create(bankAccount);
        BankUser user = bankUserService.get(username);
        bankUserService.addAccount(user, bankAccount);


        response.sendRedirect(request.getContextPath() + "/user?username=" + username);
    }
}
