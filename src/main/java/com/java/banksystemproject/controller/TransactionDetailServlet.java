package com.java.banksystemproject.controller;

import com.java.banksystemproject.model.Transaction;
import com.java.banksystemproject.model.constant.TransactionStatus;
import com.java.banksystemproject.model.constant.TransactionType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "TransactionDetailServlet", urlPatterns = {"/transaction_detail/*"})
public class TransactionDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Transaction test = Transaction.builder()
                .transactionType(TransactionType.DEPOSITS)
                .transactionDate(new Date())
                .amount(220000)
                .transactionId(123)
                .fee(200)
                .status(TransactionStatus.DONE)
                .sourceAccountNumber("123333")
                .build(); //TODO: use services
        req.setAttribute("transaction_detail", test);
        req.getRequestDispatcher("/transaction_detail.jsp").forward(req, resp);
    }
}
