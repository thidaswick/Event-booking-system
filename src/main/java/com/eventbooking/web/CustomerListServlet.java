package com.eventbooking.web;

import com.eventbooking.model.Customer;
import com.eventbooking.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * READ: lists registered customers from {@code customers.txt}.
 */
@WebServlet(name = "CustomerListServlet", urlPatterns = "/customers/list")
public class CustomerListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        CustomerService service = AppContext.customerService(getServletContext());

        String q = request.getParameter("q");
        List<Customer> customers;
        boolean searchActive = q != null && !q.isBlank();
        if (searchActive) {
            String t = q.trim();
            if (t.matches("(?i)CU\\d+")) {
                customers = service.search(t, "");
            } else {
                customers = service.search("", t);
            }
        } else {
            customers = service.listAll();
        }

        request.setAttribute("customers", customers);
        request.setAttribute("q", q == null ? "" : q);
        request.setAttribute("searchActive", searchActive);
        request.getRequestDispatcher("/WEB-INF/jsp/customers-list.jsp").forward(request, response);
    }
}
