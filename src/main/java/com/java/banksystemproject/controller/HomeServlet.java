package com.java.banksystemproject.controller;

import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.model.account.CheckingAccount;
import com.java.banksystemproject.model.account.SavingAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "Home", value = "/home")
public class HomeServlet extends HttpServlet {
    SavingAccount savingAccount1 = SavingAccount.builder().accountNumber("123").accountHolderNumber("ABC").balance(200000).active(false).build();//new SavingAccount("123", "ABC", 0.3f, 2);
    SavingAccount savingAccount2 = new SavingAccount("1234", "ABCD", 0.4f);
    CheckingAccount checkingAccount1 = new CheckingAccount("12345", "ABCDE", 2000);
    CheckingAccount checkingAccount2 = new CheckingAccount("123456", "ABCDEF", 3000);
    BankUser user;

    public void init() {
        user = BankUser.builder()
                .username("ZeD")
                .bankAccounts(Set.of(savingAccount1, savingAccount2, checkingAccount1, checkingAccount2))
                .build();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //TODO: use userService
        var users = user.getBankAccounts().stream().filter(BankAccount::isActive).collect(Collectors.toList());
        request.setAttribute("user", users);
        request.getRequestDispatcher("/home.jsp").forward(request, response);

    }
}
