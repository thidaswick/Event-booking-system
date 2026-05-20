package com.eventbooking.web;

import com.eventbooking.model.Booking;
import com.eventbooking.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * READ / SEARCH: filter by booking id or customer name (substring).
 */
@WebServlet(name = "BookingSearchServlet", urlPatterns = "/bookings/search")
public class BookingSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("adminBooking", true);
        String bookingId = request.getParameter("bookingId");
        String customerName = request.getParameter("customerName");

        request.setAttribute("bookingIdQuery", bookingId == null ? "" : bookingId);
        request.setAttribute("customerNameQuery", customerName == null ? "" : customerName);

        boolean searched = request.getParameter("search") != null;
        request.setAttribute("searched", searched);
        if (searched) {
            BookingService service = AppContext.bookingService(getServletContext());
            List<Booking> results = service.search(bookingId, customerName);
            request.setAttribute("results", results);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/booking-search.jsp").forward(request, response);
    }
}
