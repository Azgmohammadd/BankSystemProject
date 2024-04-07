package com.java.banksystemproject.controller.admin;

import com.java.banksystemproject.dao.impl.JDBC.BankAccountDaoJDBC;
import com.java.banksystemproject.dao.impl.JDBC.BankUserDaoJDBC;
import com.java.banksystemproject.model.BankUser;
import com.java.banksystemproject.model.constant.Role;
import com.java.banksystemproject.service.IBankUserService;
import com.java.banksystemproject.service.impl.BankUserService;
import com.java.banksystemproject.util.impl.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    IBankUserService bankUserService = new BankUserService(new BankUserDaoJDBC(), new BankAccountDaoJDBC());

    @Override
    public void init() throws ServletException {
        BankUser admin = BankUser.builder()
                .username("Admin")
                .password(PasswordEncoder.encodePassword("Admin"))
                .firstName("Admin")
                .lastName("Admin")
                .role(Role.ADMIN)
                .nationalCode("0")
                .build();

        bankUserService.save(admin);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BankUser> users = bankUserService.getAllUsers().stream().filter(bankUser -> Role.USER.equals(bankUser.getRole())).toList();

        request.setAttribute("users", users);
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }
}
