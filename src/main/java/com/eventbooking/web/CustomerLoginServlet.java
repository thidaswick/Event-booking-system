package com.eventbooking.web;

import com.eventbooking.model.Customer;
import com.eventbooking.service.AdminService;
import com.eventbooking.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

@WebServlet(name = "CustomerLoginServlet", urlPatterns = "/login")
public class CustomerLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (CustomerSession.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/account");
            return;
        }
        request.setAttribute("pageTitle", "Sign in — LensCraft Studio");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
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
            AdminService adminService = AppContext.adminService(getServletContext());
            if (adminService.isAdminEmail(email)) {
                response.sendRedirect(request.getContextPath() + "/admin/login?email="
                        + java.net.URLEncoder.encode(email.trim(), java.nio.charset.StandardCharsets.UTF_8)
                        + "&msg=useadmin");
                return;
            }
            CustomerService service = AppContext.customerService(getServletContext());
            var found = service.authenticate(email, pw);
            if (found.isEmpty()) {
                forwardError(request, response, email, "Invalid email or password.");
                return;
            }
            CustomerSession.login(request, found.get());
            response.sendRedirect(request.getContextPath() + "/account");
        } catch (GeneralSecurityException ex) {
            forwardError(request, response, email, "Could not verify password. Try again.");
        } finally {
            java.util.Arrays.fill(pw, '\0');
        }
    }

    private static void forwardError(HttpServletRequest request, HttpServletResponse response,
                                     String email, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.setAttribute("email", email == null ? "" : email);
        request.setAttribute("pageTitle", "Sign in — LensCraft Studio");
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }
}
