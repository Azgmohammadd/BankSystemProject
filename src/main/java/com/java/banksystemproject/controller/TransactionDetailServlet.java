package com.java.banksystemproject.controller;

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

@WebServlet(name = "TransactionDetailServlet", urlPatterns = {"/transaction_detail/*"})
public class TransactionDetailServlet extends HttpServlet {
    private final ITransactionService transactionService = new TransactionService(new TransactionDaoJDBC());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String transactionID = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);

        Transaction transaction = transactionService.get(transactionID);
        req.setAttribute("transaction_detail", transaction);
        req.getRequestDispatcher("/transaction_detail.jsp").forward(req, resp);
    }
}
