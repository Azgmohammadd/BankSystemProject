package com.java.banksystemproject.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet(name = "ErrorHandlerServlet", value = "/ErrorHandler")
public class ErrorHandlerServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }
    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        request.setAttribute("error", throwable.getMessage());
        request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
    }
}

