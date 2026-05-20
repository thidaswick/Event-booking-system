package com.eventbooking.web;

import com.eventbooking.model.Booking;
import com.eventbooking.model.Customer;
import com.eventbooking.service.BookingService;
import com.eventbooking.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

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
        Booking booking = new Booking();
        attachLoggedInCustomer(request, booking);
        request.setAttribute("booking", booking);
        request.setAttribute("formAction", request.getContextPath() + "/bookings/create");
        AppContext.attachCustomersForBookingForm(getServletContext(), request);
        request.getRequestDispatcher("/WEB-INF/jsp/booking-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        BookingService service = AppContext.bookingService(getServletContext());
        Booking input = readBookingFromRequest(request);
        applySessionCustomerId(request, input);
        try {
            service.createNew(input);
            if (CustomerSession.isLoggedIn(request)) {
                response.sendRedirect(request.getContextPath() + "/account?msg=created");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp?msg=bookingcreated");
            }
        } catch (IOException ex) {
            request.setAttribute("error", "Could not save booking: " + ex.getMessage());
            request.setAttribute("formTitle", "New booking");
            request.setAttribute("booking", input);
            request.setAttribute("formAction", request.getContextPath() + "/bookings/create");
            AppContext.attachCustomersForBookingForm(getServletContext(), request);
            request.getRequestDispatcher("/WEB-INF/jsp/booking-form.jsp").forward(request, response);
        }
    }

    static Booking readBookingFromRequest(HttpServletRequest request) {
        Booking b = new Booking();
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

    private static void attachLoggedInCustomer(HttpServletRequest request, Booking booking) throws IOException {
        Optional<String> id = CustomerSession.currentCustomerId(request);
        if (id.isEmpty()) {
            return;
        }
        CustomerService customers = AppContext.customerService(request.getServletContext());
        customers.findById(id.get()).ifPresent(c -> {
            booking.setCustomerId(c.getCustomerId());
            booking.setCustomerName(c.getFullName());
            booking.setPhone(c.getPhone());
            request.setAttribute("loggedInCustomerId", c.getCustomerId());
        });
    }

    private static void applySessionCustomerId(HttpServletRequest request, Booking booking) {
        if (booking.getCustomerId() != null && !booking.getCustomerId().isBlank()) {
            return;
        }
        CustomerSession.currentCustomerId(request).ifPresent(booking::setCustomerId);
    }
}
