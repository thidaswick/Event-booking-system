package com.eventbooking.web;

import com.eventbooking.service.BookingService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * DELETE: removes one booking from the text file (POST only — safer than GET).
 */
@WebServlet(name = "BookingDeleteServlet", urlPatterns = "/bookings/delete")
public class BookingDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("bookingId");
        BookingService service = AppContext.bookingService(getServletContext());
        try {
            service.deleteById(id);
            response.sendRedirect(request.getContextPath() + "/bookings/list?msg=deleted");
        } catch (Exception ex) {
            response.sendRedirect(request.getContextPath() + "/bookings/list?msg=deletefailed");
        }
    }
}
