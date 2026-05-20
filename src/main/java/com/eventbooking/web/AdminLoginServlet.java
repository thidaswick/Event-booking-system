package com.eventbooking.web;

import com.eventbooking.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

@WebServlet(name = "AdminLoginServlet", urlPatterns = "/admin/login")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (AdminSession.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }
        request.setAttribute("pageTitle", "Admin sign in — LensCraft Studio");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (password == null) {
            forwardError(request, response, email, "Password is required.");
            return;
        }
        char[] pw = password.toCharArray();
        try {
            AdminService service = AppContext.adminService(getServletContext());
            if (service.authenticate(email, pw)) {
                AdminSession.login(request);
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                forwardError(request, response, email, "Invalid admin email or password.");
            }
        } catch (GeneralSecurityException ex) {
            forwardError(request, response, email, "Could not verify password.");
        } finally {
            java.util.Arrays.fill(pw, '\0');
        }
    }

    private static void forwardError(HttpServletRequest request, HttpServletResponse response,
                                     String email, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.setAttribute("email", email == null ? "" : email);
        request.setAttribute("pageTitle", "Admin sign in — LensCraft Studio");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(request, response);
    }
}
