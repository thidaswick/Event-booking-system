package com.eventbooking.util;

import jakarta.servlet.ServletContext;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Resolves where bookings.txt lives on disk. Uses WEB-INF/data under the deployed web app when possible.
 */
public final class BookingDataPaths {

    private BookingDataPaths() {
    }

    public static Path bookingsFile(ServletContext context) {
        String real = context.getRealPath("/WEB-INF/data/bookings.txt");
        if (real != null) {
            return Paths.get(real);
        }
        // Fallback when running in some embedded setups where getRealPath returns null.
        return Paths.get(System.getProperty("user.dir"), "bookings-data", "bookings.txt");
    }

    public static Path customersFile(ServletContext context) {
        String real = context.getRealPath("/WEB-INF/data/customers.txt");
        if (real != null) {
            return Paths.get(real);
        }
        return Paths.get(System.getProperty("user.dir"), "bookings-data", "customers.txt");
    }

    public static Path contactsFile(ServletContext context) {
        String real = context.getRealPath("/WEB-INF/data/contacts.txt");
        if (real != null) {
            return Paths.get(real);
        }
        return Paths.get(System.getProperty("user.dir"), "bookings-data", "contacts.txt");
    }
}
