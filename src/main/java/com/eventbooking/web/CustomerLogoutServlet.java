package com.eventbooking.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CustomerLogoutServlet", urlPatterns = "/logout")
public class CustomerLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomerSession.logout(request);
        response.sendRedirect(request.getContextPath() + "/login?msg=loggedout");
    }
}
