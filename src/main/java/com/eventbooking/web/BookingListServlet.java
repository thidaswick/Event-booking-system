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
 * READ: lists all bookings from the text file.
 */
@WebServlet(name = "BookingListServlet", urlPatterns = "/bookings/list")
public class BookingListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("adminBooking", true);
        BookingService service = AppContext.bookingService(getServletContext());

        String q = request.getParameter("q");
        List<Booking> bookings;
        boolean searchActive = q != null && !q.isBlank();
        if (searchActive) {
            String t = q.trim();
            if (t.matches("(?i)BK\\d+")) {
                bookings = service.search(t, "");
            } else {
                bookings = service.search("", t);
            }
        } else {
            bookings = service.listAll();
        }

        request.setAttribute("bookings", bookings);
        request.setAttribute("q", q == null ? "" : q);
        request.setAttribute("searchActive", searchActive);
        request.getRequestDispatcher("/WEB-INF/jsp/bookings-list.jsp").forward(request, response);
    }
}
