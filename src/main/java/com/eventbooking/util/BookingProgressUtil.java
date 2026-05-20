package com.eventbooking.util;

import com.eventbooking.model.Booking;
import com.eventbooking.model.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class BookingProgressUtil {

    public record Progress(int percent, String label) {
    }

    private BookingProgressUtil() {
    }

    public static Progress forBooking(Booking booking) {
        LocalDate eventDate = parseEventDate(booking == null ? null : booking.getEventDate());
        if (eventDate == null) {
            return new Progress(25, "Booked");
        }
        LocalDate today = LocalDate.now();
        if (!eventDate.isAfter(today)) {
            return new Progress(100, "Completed");
        }
        long daysUntil = java.time.temporal.ChronoUnit.DAYS.between(today, eventDate);
        if (daysUntil <= 7) {
            return new Progress(85, "Shoot week");
        }
        if (daysUntil <= 30) {
            return new Progress(60, "In production");
        }
        return new Progress(35, "Confirmed");
    }

    public static boolean belongsToCustomer(Booking booking, Customer customer) {
        if (booking == null || customer == null) {
            return false;
        }
        String accountId = customer.getCustomerId();
        if (accountId != null && !accountId.isBlank()
                && booking.getCustomerId() != null
                && accountId.equalsIgnoreCase(booking.getCustomerId().trim())) {
            return true;
        }
        String custPhone = normalizePhone(customer.getPhone());
        String bookPhone = normalizePhone(booking.getPhone());
        if (!custPhone.isEmpty() && custPhone.equals(bookPhone)) {
            return true;
        }
        if (customer.getFullName() != null && booking.getCustomerName() != null
                && customer.getFullName().trim().equalsIgnoreCase(booking.getCustomerName().trim())) {
            return true;
        }
        return false;
    }

    private static LocalDate parseEventDate(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(raw.trim());
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    private static String normalizePhone(String phone) {
        if (phone == null) {
            return "";
        }
        return phone.replaceAll("\\D", "");
    }
}
