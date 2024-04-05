package com.java.banksystemproject.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.banksystemproject.dao.impl.JDBC.TransactionDaoJDBC;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.service.ITransactionService;
import com.java.banksystemproject.service.impl.TransactionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/accountTransactions")
public class AccountTransactionsServlet extends HttpServlet {
    private final ITransactionService transactionService = new TransactionService(new TransactionDaoJDBC());
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Transaction> transactions = transactionService.getAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonTransactions = objectMapper.writeValueAsString(transactions);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(jsonTransactions);
        out.flush();
    }
}