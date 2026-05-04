package com.eventbooking.web;

import com.eventbooking.model.Customer;
import com.eventbooking.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Customer self-registration (GET form, POST create account).
 * Mapped in WEB-INF/web.xml to {@code /register} for reliable Jetty deployment.
 */
public class CustomerRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("pageTitle", "Create account — LensCraft Studio");
        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        if (password == null || confirm == null || !password.equals(confirm)) {
            forwardWithError(request, response, "Passwords do not match.", fullName, email, phone);
            return;
        }

        CustomerService service = AppContext.customerService(getServletContext());
        try {
            char[] pw = password.toCharArray();
            try {
                Customer created = service.register(fullName, email, phone, pw);
                response.sendRedirect(request.getContextPath() + "/register?created=1&id=" + created.getCustomerId());
            } finally {
                java.util.Arrays.fill(pw, '\0');
            }
        } catch (IllegalArgumentException ex) {
            forwardWithError(request, response, ex.getMessage(), fullName, email, phone);
        } catch (GeneralSecurityException ex) {
            forwardWithError(request, response, "Could not secure password. Try again.", fullName, email, phone);
        } catch (IOException ex) {
            forwardWithError(request, response, "Could not save account. Try again.", fullName, email, phone);
        }
    }

    private static void forwardWithError(HttpServletRequest request, HttpServletResponse response,
                                         String error, String fullName, String email, String phone)
            throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("fullName", fullName == null ? "" : fullName);
        request.setAttribute("email", email == null ? "" : email);
        request.setAttribute("phone", phone == null ? "" : phone);
        request.setAttribute("pageTitle", "Create account — LensCraft Studio");
        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    }
}
