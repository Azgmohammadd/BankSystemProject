package com.java.banksystemproject.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.model.constant.TransactionType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@WebServlet("/accountTransactions")
public class AccountTransactionsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO: use transaction service
        List<Transaction> transactions = List.of(Transaction.builder()
                .transactionType(TransactionType.DEPOSITS)
                .transactionDate(new Date())
                .amount(220000).transactionId(123)
                .fee(200)
                .status(TransactionStatus.DONE)
                .sourceAccountNumber("123333")
                .build());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonTransactions = objectMapper.writeValueAsString(transactions);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(jsonTransactions);
        out.flush();
    }
}