package com.eventbooking.web;

import com.eventbooking.service.BookingService;
import com.eventbooking.service.ContactService;
import com.eventbooking.service.CustomerService;
import com.eventbooking.service.PackageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = "/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        CustomerService customers = AppContext.customerService(getServletContext());
        BookingService bookings = AppContext.bookingService(getServletContext());
        ContactService contacts = AppContext.contactService(getServletContext());
        PackageService packages = AppContext.packageService(getServletContext());

        request.setAttribute("customerCount", customers.listAll().size());
        request.setAttribute("bookingCount", bookings.listAll().size());
        request.setAttribute("contactCount", contacts.listAll().size());
        request.setAttribute("packageCount", packages.listAll().size());
        request.setAttribute("pageTitle", "Admin dashboard — LensCraft Studio");
        request.getRequestDispatcher("/WEB-INF/jsp/admin/dashboard.jsp").forward(request, response);
    }
}
