package com.eventbooking.web;

import com.eventbooking.model.Customer;
import com.eventbooking.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

/**
 * UPDATE: edit a registered customer in {@code customers.txt}.
 */
@WebServlet(name = "CustomerEditServlet", urlPatterns = "/customers/edit")
public class CustomerEditServlet extends HttpServlet {

    private static boolean fromAdmin(HttpServletRequest request) {
        return "admin".equals(request.getParameter("from"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean admin = fromAdmin(request);
        if (admin && !AdminSession.requireAdmin(request, response)) {
            return;
        }
        String id = request.getParameter("customerId");
        CustomerService service = AppContext.customerService(getServletContext());
        Optional<Customer> found = service.findById(id == null ? "" : id);
        if (found.isEmpty()) {
            response.sendRedirect(admin
                    ? AdminSession.customersRedirect(request, "notfound")
                    : request.getContextPath() + "/register?msg=notfound");
            return;
        }
        request.setAttribute("fromAdmin", admin);
        request.setAttribute("formTitle", admin ? "Edit customer (admin)" : "Edit account");
        request.setAttribute("customer", found.get());
        request.setAttribute("formAction", request.getContextPath() + "/customers/edit"
                + (admin ? "?from=admin" : ""));
        request.getRequestDispatcher("/WEB-INF/jsp/customer-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean admin = fromAdmin(request);
        if (admin && !AdminSession.requireAdmin(request, response)) {
            return;
        }
        String id = request.getParameter("customerId");
        if (id == null || id.isBlank()) {
            response.sendRedirect(admin
                    ? AdminSession.customersRedirect(request, "notfound")
                    : request.getContextPath() + "/register?msg=notfound");
            return;
        }

        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");
        if (password != null && !password.isBlank() && !password.equals(confirm)) {
            forwardWithError(request, response, id, admin, "Passwords do not match.");
            return;
        }

        CustomerService service = AppContext.customerService(getServletContext());
        char[] pw = (password == null || password.isBlank()) ? new char[0] : password.toCharArray();
        try {
            try {
                service.update(id, request.getParameter("fullName"), request.getParameter("email"),
                        request.getParameter("phone"), pw);
                response.sendRedirect(admin
                        ? AdminSession.customersRedirect(request, "updated")
                        : request.getContextPath() + "/register?msg=updated");
            } finally {
                if (pw.length > 0) {
                    java.util.Arrays.fill(pw, '\0');
                }
            }
        } catch (IllegalArgumentException ex) {
            forwardWithError(request, response, id, admin, ex.getMessage());
        } catch (GeneralSecurityException ex) {
            forwardWithError(request, response, id, admin, "Could not update password. Try again.");
        } catch (IOException ex) {
            forwardWithError(request, response, id, admin, "Could not save account: " + ex.getMessage());
        }
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response,
                                  String customerId, boolean admin, String error)
            throws ServletException, IOException {
        Customer c = new Customer();
        c.setCustomerId(customerId);
        c.setFullName(request.getParameter("fullName"));
        c.setEmail(request.getParameter("email"));
        c.setPhone(request.getParameter("phone"));
        request.setAttribute("error", error);
        request.setAttribute("fromAdmin", admin);
        request.setAttribute("formTitle", admin ? "Edit customer (admin)" : "Edit account");
        request.setAttribute("customer", c);
        request.setAttribute("formAction", request.getContextPath() + "/customers/edit"
                + (admin ? "?from=admin" : ""));
        request.getRequestDispatcher("/WEB-INF/jsp/customer-form.jsp").forward(request, response);
    }
}
