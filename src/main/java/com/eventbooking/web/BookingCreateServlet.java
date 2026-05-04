package com.eventbooking.web;

import com.eventbooking.model.Booking;
import com.eventbooking.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * CREATE: GET shows empty form, POST saves a new booking (id is generated).
 */
@WebServlet(name = "BookingCreateServlet", urlPatterns = "/bookings/create")
public class BookingCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("formTitle", "New booking");
        request.setAttribute("booking", new Booking());
        request.setAttribute("formAction", request.getContextPath() + "/bookings/create");
        AppContext.attachCustomersForBookingForm(getServletContext(), request);
        request.getRequestDispatcher("/WEB-INF/jsp/booking-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        BookingService service = AppContext.bookingService(getServletContext());
        try {
            Booking input = readBookingFromRequest(request);
            service.createNew(input);
            response.sendRedirect(request.getContextPath() + "/bookings/list?msg=created");
        } catch (IOException ex) {
            request.setAttribute("error", "Could not save booking: " + ex.getMessage());
            request.setAttribute("formTitle", "New booking");
            request.setAttribute("booking", readBookingFromRequest(request));
            request.setAttribute("formAction", request.getContextPath() + "/bookings/create");
            AppContext.attachCustomersForBookingForm(getServletContext(), request);
            request.getRequestDispatcher("/WEB-INF/jsp/booking-form.jsp").forward(request, response);
        }
    }

    private static Booking readBookingFromRequest(HttpServletRequest request) {
        Booking b = new Booking();
        b.setCustomerName(request.getParameter("customerName"));
        b.setPhone(request.getParameter("phone"));
        b.setEventType(request.getParameter("eventType"));
        b.setEventDate(request.getParameter("eventDate"));
        b.setLocation(request.getParameter("location"));
        b.setServiceType(request.getParameter("serviceType"));
        b.setPackageName(request.getParameter("packageName"));
        b.setSpecialNotes(request.getParameter("specialNotes"));
        return b;
    }
}
