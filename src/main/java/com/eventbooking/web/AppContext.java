package com.eventbooking.web;

import com.eventbooking.service.BookingService;
import com.eventbooking.util.BookingDataPaths;
import com.eventbooking.util.BookingFileStore;

import jakarta.servlet.ServletContext;

import java.nio.file.Path;

/**
 * Lazily creates one BookingService per web application (stored in ServletContext).
 */
public final class AppContext {

    private static final String KEY = "bookingService";

    private AppContext() {
    }

    public static BookingService bookingService(ServletContext servletContext) {
        synchronized (servletContext) {
            Object existing = servletContext.getAttribute(KEY);
            if (existing instanceof BookingService bs) {
                return bs;
            }
            Path path = BookingDataPaths.bookingsFile(servletContext);
            BookingFileStore store = new BookingFileStore(path);
            BookingService service = new BookingService(store);
            servletContext.setAttribute(KEY, service);
            return service;
        }
    }
}
