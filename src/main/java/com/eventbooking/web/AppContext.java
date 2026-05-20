package com.eventbooking.web;

import com.eventbooking.model.Customer;
import com.eventbooking.model.StudioPackage;
import com.eventbooking.service.AdminService;
import com.eventbooking.service.BookingService;
import com.eventbooking.service.ContactService;
import com.eventbooking.service.CustomerService;
import com.eventbooking.service.PackageService;
import com.eventbooking.util.AdminFileStore;
import com.eventbooking.util.BookingDataPaths;
import com.eventbooking.util.BookingFileStore;
import com.eventbooking.util.ContactFileStore;
import com.eventbooking.util.CustomerFileStore;
import com.eventbooking.util.PackageFileStore;

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
    private static final String ADMIN_KEY = "adminService";
    private static final String PACKAGE_KEY = "packageService";

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

    public static AdminService adminService(ServletContext servletContext) {
        synchronized (servletContext) {
            Object existing = servletContext.getAttribute(ADMIN_KEY);
            if (existing instanceof AdminService) {
                return (AdminService) existing;
            }
            Path path = BookingDataPaths.adminFile(servletContext);
            AdminFileStore store = new AdminFileStore(path);
            AdminService service = new AdminService(store);
            servletContext.setAttribute(ADMIN_KEY, service);
            return service;
        }
    }

    public static PackageService packageService(ServletContext servletContext) {
        synchronized (servletContext) {
            Object existing = servletContext.getAttribute(PACKAGE_KEY);
            if (existing instanceof PackageService) {
                return (PackageService) existing;
            }
            Path path = BookingDataPaths.packagesFile(servletContext);
            PackageFileStore store = new PackageFileStore(path);
            PackageService service = new PackageService(store);
            servletContext.setAttribute(PACKAGE_KEY, service);
            return service;
        }
    }

    /** Loads sellable packages into request attribute "packages" for booking form datalist. */
    public static void attachPackagesForBookingForm(ServletContext servletContext, HttpServletRequest request) {
        try {
            List<StudioPackage> list = packageService(servletContext).listAll();
            request.setAttribute("packages", list);
        } catch (IOException e) {
            request.setAttribute("packages", Collections.emptyList());
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
