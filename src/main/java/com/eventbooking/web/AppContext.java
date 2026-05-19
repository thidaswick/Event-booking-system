package com.eventbooking.web;

import com.eventbooking.model.Customer;
import com.eventbooking.service.BookingService;
import com.eventbooking.service.ContactService;
import com.eventbooking.service.CustomerService;
import com.eventbooking.util.BookingDataPaths;
import com.eventbooking.util.BookingFileStore;
import com.eventbooking.util.ContactFileStore;
import com.eventbooking.util.CustomerFileStore;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/** Lazily creates BookingService and CustomerService instances cached on the ServletContext. */
public final class AppContext {

    private static final String KEY = "bookingService";
    private static final String CUSTOMER_KEY = "customerService";
    private static final String CONTACT_KEY = "contactService";

    private AppContext() {
    }

    public static BookingService bookingService(ServletContext servletContext) {
        synchronized (servletContext) {
            Object existing = servletContext.getAttribute(KEY);
            if (existing instanceof BookingService) {
                return (BookingService) existing;
            }
            Path path = BookingDataPaths.bookingsFile(servletContext);
            BookingFileStore store = new BookingFileStore(path);
            BookingService service = new BookingService(store);
            servletContext.setAttribute(KEY, service);
            return service;
        }
    }

    public static CustomerService customerService(ServletContext servletContext) {
        synchronized (servletContext) {
            Object existing = servletContext.getAttribute(CUSTOMER_KEY);
            if (existing instanceof CustomerService) {
                return (CustomerService) existing;
            }
            Path path = BookingDataPaths.customersFile(servletContext);
            CustomerFileStore store = new CustomerFileStore(path);
            CustomerService service = new CustomerService(store);
            servletContext.setAttribute(CUSTOMER_KEY, service);
            return service;
        }
    }

    public static ContactService contactService(ServletContext servletContext) {
        synchronized (servletContext) {
            Object existing = servletContext.getAttribute(CONTACT_KEY);
            if (existing instanceof ContactService) {
                return (ContactService) existing;
            }
            Path path = BookingDataPaths.contactsFile(servletContext);
            ContactFileStore store = new ContactFileStore(path);
            ContactService service = new ContactService(store);
            servletContext.setAttribute(CONTACT_KEY, service);
            return service;
        }
    }

    /** Loads registered customers into request attribute "customers" for booking create/edit JSPs. */
    public static void attachCustomersForBookingForm(ServletContext servletContext, HttpServletRequest request) {
        try {
            List<Customer> list = customerService(servletContext).listAll();
            request.setAttribute("customers", list);
        } catch (IOException e) {
            request.setAttribute("customers", Collections.emptyList());
        }
    }
}
