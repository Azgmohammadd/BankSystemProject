package com.java.banksystemproject.controller;

import com.java.banksystemproject.dao.IBankAccountDao;
import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.account.BankAccount;
import com.java.banksystemproject.service.account.IBankAccountService;
import com.java.banksystemproject.service.account.ISavingAccountService;
import com.java.banksystemproject.service.account.factory.BankAccountServiceFactory;
import com.java.banksystemproject.service.account.factory.CheckingAccountServiceFactory;
import com.java.banksystemproject.service.account.factory.SavingAccountServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TransactionServlet", urlPatterns = {"/transaction/*"})
public class TransactionServlet extends HttpServlet {
    private final IBankAccountService bankAccountService = new BankAccountServiceFactory().getJDBC();
    private final IBankAccountService checkingAccountService = new CheckingAccountServiceFactory().getJDBC();
    private final ISavingAccountService savingAccountService = new SavingAccountServiceFactory().getJDBC();
    private final IBankAccountDao bankAccountDao = new BankAccountDaoJDBC();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String transactionType = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);
        double amount = Double.parseDouble(req.getParameter("amount"));
        String accountId = req.getParameter("selectedAccountNumber");
        BankAccount account = bankAccountDao.get(accountId).get();

        Transaction transaction = null;

        switch (transactionType) {
            case "withdraw" -> {
                if (account.getType().equals("Checking")) {
                     transaction = checkingAccountService.withdraw(account, amount);
                } else if (account.getType().equals("Saving")){
                    transaction = savingAccountService.withdraw(account, amount);
                } else {
                    transaction = bankAccountService.withdraw(account, amount);
                }
            }
            case "deposit" -> {
                if (account.getType().equals("Checking")) {
                    transaction = checkingAccountService.deposit(account, amount);
                } else if (account.getType().equals("Saving")){
                    transaction = savingAccountService.deposit(account, amount);
                }else {
                    transaction = bankAccountService.deposit(account, amount);
                }
            }
            case "balance" -> {
                transaction = bankAccountService.getBalance(account);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/transaction_detail/" + transaction.getTransactionId());
    }
}
