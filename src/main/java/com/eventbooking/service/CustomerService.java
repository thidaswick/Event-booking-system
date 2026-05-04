package com.eventbooking.service;

import com.eventbooking.model.Customer;
import com.eventbooking.util.CustomerFileStore;
import com.eventbooking.util.PasswordHasher;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Registers customers and enforces unique email addresses.
 */
public class CustomerService {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");

    private final CustomerFileStore store;

    public CustomerService(CustomerFileStore store) {
        this.store = store;
    }

    public List<Customer> listAll() throws IOException {
        return store.readAll();
    }

    /**
     * Creates a new customer row. Password is never stored in plain text.
     */
    public Customer register(String fullName, String email, String phone, char[] password)
            throws IOException, GeneralSecurityException {
        synchronized (store) {
            if (fullName == null || fullName.isBlank()) {
                throw new IllegalArgumentException("Full name is required.");
            }
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email is required.");
            }
            String normalizedEmail = email.trim().toLowerCase();
            if (!EMAIL.matcher(normalizedEmail).matches()) {
                throw new IllegalArgumentException("Please enter a valid email address.");
            }
            if (phone == null || phone.isBlank()) {
                throw new IllegalArgumentException("Phone is required.");
            }
            if (password == null || password.length < 8) {
                throw new IllegalArgumentException("Password must be at least 8 characters.");
            }

            List<Customer> all = store.readAll();
            for (Customer c : all) {
                if (c.getEmail() != null && c.getEmail().equalsIgnoreCase(normalizedEmail)) {
                    throw new IllegalArgumentException("An account with this email already exists.");
                }
            }

            byte[] salt = PasswordHasher.newSalt();
            byte[] hash = PasswordHasher.derive(password, salt);

            Customer c = new Customer();
            c.setCustomerId(store.nextCustomerId());
            c.setFullName(fullName.trim());
            c.setEmail(normalizedEmail);
            c.setPhone(phone.trim());
            c.setSaltBase64(PasswordHasher.toBase64(salt));
            c.setPasswordHashBase64(PasswordHasher.toBase64(hash));
            c.setCreatedAt(Instant.now().toString());

            all.add(c);
            store.writeAll(all);
            return c;
        }
    }
}
