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
        request.setCharacterEncoding("UTF-8");
        BookingService service = AppContext.bookingService(getServletContext());
        List<Booking> bookings = service.listAll();
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/WEB-INF/jsp/bookings-list.jsp").forward(request, response);
    }
}
