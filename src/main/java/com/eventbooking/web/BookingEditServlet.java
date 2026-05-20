package com.eventbooking.web;

import com.eventbooking.model.Booking;
import com.eventbooking.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

/**
 * UPDATE: GET loads one booking into the form, POST writes changes back to the file.
 */
@WebServlet(name = "BookingEditServlet", urlPatterns = "/bookings/edit")
public class BookingEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("adminBooking", true);
        String id = request.getParameter("bookingId");
        BookingService service = AppContext.bookingService(getServletContext());
        Optional<Booking> found = service.findById(id == null ? "" : id);
        if (found.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/bookings/list?msg=notfound");
            return;
        }
        request.setAttribute("formTitle", "Edit booking");
        request.setAttribute("booking", found.get());
        request.setAttribute("formAction", request.getContextPath() + "/bookings/edit");
        AppContext.attachCustomersForBookingForm(getServletContext(), request);
        AppContext.attachPackagesForBookingForm(getServletContext(), request);
        request.getRequestDispatcher("/WEB-INF/jsp/booking-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AdminSession.requireAdmin(request, response)) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("bookingId");
        if (id == null || id.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/bookings/list?msg=notfound");
            return;
        }
        BookingService service = AppContext.bookingService(getServletContext());
        try {
            Booking updated = readBooking(request, id);
            service.update(updated);
            response.sendRedirect(request.getContextPath() + "/bookings/list?msg=updated");
        } catch (IOException | IllegalArgumentException ex) {
            request.setAttribute("error", "Could not update: " + ex.getMessage());
            request.setAttribute("formTitle", "Edit booking");
            request.setAttribute("booking", repopulate(request, id));
            request.setAttribute("formAction", request.getContextPath() + "/bookings/edit");
            AppContext.attachCustomersForBookingForm(getServletContext(), request);
            AppContext.attachPackagesForBookingForm(getServletContext(), request);
            request.getRequestDispatcher("/WEB-INF/jsp/booking-form.jsp").forward(request, response);
        }
    }

    private static Booking readBooking(HttpServletRequest request, String bookingId) {
        Booking b = new Booking();
        b.setBookingId(bookingId);
        b.setCustomerId(request.getParameter("customerId"));
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

    private static Booking repopulate(HttpServletRequest request, String id) {
        return readBooking(request, id);
    }
}
