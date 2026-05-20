package com.eventbooking.web;

import com.eventbooking.model.Booking;
import com.eventbooking.model.Customer;
import com.eventbooking.service.BookingService;
import com.eventbooking.service.CustomerService;
import com.eventbooking.util.BookingProgressUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Personal account dashboard: registered customer's bookings with progress bars.
 */
@WebServlet(name = "CustomerAccountServlet", urlPatterns = "/account")
public class CustomerAccountServlet extends HttpServlet {

    public static final class BookingProgressRow {
        private final Booking booking;
        private final int percent;
        private final String stageLabel;

        public BookingProgressRow(Booking booking, int percent, String stageLabel) {
            this.booking = booking;
            this.percent = percent;
            this.stageLabel = stageLabel;
        }

        public Booking getBooking() {
            return booking;
        }

        public int getPercent() {
            return percent;
        }

        public String getStageLabel() {
            return stageLabel;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!CustomerSession.requireLogin(request, response)) {
            return;
        }
        String customerId = CustomerSession.currentCustomerId(request).orElse("");
        CustomerService customerService = AppContext.customerService(getServletContext());
        Optional<Customer> customer = customerService.findById(customerId);
        if (customer.isEmpty()) {
            CustomerSession.logout(request);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Booking> bookings = AppContext.bookingService(getServletContext()).listForCustomer(customer.get());
        List<BookingProgressRow> rows = new ArrayList<>();
        int sum = 0;
        for (Booking b : bookings) {
            BookingProgressUtil.Progress p = BookingProgressUtil.forBooking(b);
            rows.add(new BookingProgressRow(b, p.percent(), p.label()));
            sum += p.percent();
        }
        int overall = rows.isEmpty() ? 0 : sum / rows.size();

        request.setAttribute("customer", customer.get());
        request.setAttribute("bookingRows", rows);
        request.setAttribute("overallPercent", overall);
        request.setAttribute("bookingCount", rows.size());
        request.setAttribute("pageTitle", "My account — LensCraft Studio");
        request.getRequestDispatcher("/WEB-INF/jsp/my-account.jsp").forward(request, response);
    }
}
