package com.eventbooking.web;

import com.eventbooking.model.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

/** HttpSession helpers for logged-in registered customers. */
public final class CustomerSession {

    public static final String ATTR_CUSTOMER_ID = "customerId";

    private CustomerSession() {
    }

    public static void login(HttpServletRequest request, Customer customer) {
        HttpSession session = request.getSession(true);
        session.setAttribute(ATTR_CUSTOMER_ID, customer.getCustomerId());
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public static Optional<String> currentCustomerId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Optional.empty();
        }
        Object id = session.getAttribute(ATTR_CUSTOMER_ID);
        if (id instanceof String s && !s.isBlank()) {
            return Optional.of(s.trim());
        }
        return Optional.empty();
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return currentCustomerId(request).isPresent();
    }

    /** Redirects to /login if not signed in. Returns false when redirect was sent. */
    public static boolean requireLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (isLoggedIn(request)) {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
