package com.eventbooking.service;

import com.eventbooking.model.Customer;
import com.eventbooking.util.CustomerFileStore;
import com.eventbooking.util.PasswordHasher;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Customer account persistence in {@code customers.txt} (same file-handling pattern as bookings).
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

    public Optional<Customer> findById(String customerId) throws IOException {
        if (customerId == null || customerId.isBlank()) {
            return Optional.empty();
        }
        String target = customerId.trim();
        for (Customer c : store.readAll()) {
            if (target.equalsIgnoreCase(c.getCustomerId())) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public List<Customer> search(String customerIdQuery, String textQuery) throws IOException {
        List<Customer> out = new ArrayList<>();
        for (Customer c : store.readAll()) {
            if (c.matchesSearch(
                    customerIdQuery == null ? "" : customerIdQuery,
                    textQuery == null ? "" : textQuery)) {
                out.add(c);
            }
        }
        return out;
    }

    /**
     * Creates a new customer row. Password is never stored in plain text.
     */
    public Customer register(String fullName, String email, String phone, char[] password)
            throws IOException, GeneralSecurityException {
        synchronized (store) {
            validateNewAccount(fullName, email, phone, password);
            String normalizedEmail = email.trim().toLowerCase();
            ensureEmailAvailable(normalizedEmail, null);

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

            List<Customer> all = store.readAll();
            all.add(c);
            store.writeAll(all);
            return c;
        }
    }

    /**
     * Updates profile fields. If {@code newPassword} is non-empty, replaces the stored hash.
     */
    public void update(String customerId, String fullName, String email, String phone, char[] newPassword)
            throws IOException, GeneralSecurityException {
        synchronized (store) {
            if (customerId == null || customerId.isBlank()) {
                throw new IllegalArgumentException("Customer id is required.");
            }
            validateProfile(fullName, email, phone);
            String normalizedEmail = email.trim().toLowerCase();
            ensureEmailAvailable(normalizedEmail, customerId.trim());

            List<Customer> all = store.readAll();
            List<Customer> next = new ArrayList<>();
            boolean replaced = false;
            for (Customer existing : all) {
                if (existing.getCustomerId() != null
                        && existing.getCustomerId().equalsIgnoreCase(customerId.trim())) {
                    Customer updated = new Customer();
                    updated.setCustomerId(existing.getCustomerId());
                    updated.setFullName(fullName.trim());
                    updated.setEmail(normalizedEmail);
                    updated.setPhone(phone.trim());
                    updated.setCreatedAt(existing.getCreatedAt());
                    if (newPassword != null && newPassword.length > 0) {
                        if (newPassword.length < 8) {
                            throw new IllegalArgumentException("Password must be at least 8 characters.");
                        }
                        byte[] salt = PasswordHasher.newSalt();
                        byte[] hash = PasswordHasher.derive(newPassword, salt);
                        updated.setSaltBase64(PasswordHasher.toBase64(salt));
                        updated.setPasswordHashBase64(PasswordHasher.toBase64(hash));
                    } else {
                        updated.setSaltBase64(existing.getSaltBase64());
                        updated.setPasswordHashBase64(existing.getPasswordHashBase64());
                    }
                    next.add(updated);
                    replaced = true;
                } else {
                    next.add(existing);
                }
            }
            if (!replaced) {
                throw new IllegalArgumentException("Customer not found: " + customerId);
            }
            store.writeAll(next);
        }
    }

    public void deleteById(String customerId) throws IOException {
        synchronized (store) {
            if (customerId == null || customerId.isBlank()) {
                throw new IllegalArgumentException("customerId is required");
            }
            List<Customer> all = store.readAll();
            List<Customer> next = new ArrayList<>();
            for (Customer c : all) {
                if (c.getCustomerId() == null || !c.getCustomerId().equalsIgnoreCase(customerId.trim())) {
                    next.add(c);
                }
            }
            if (next.size() == all.size()) {
                throw new IllegalArgumentException("Customer not found: " + customerId);
            }
            store.writeAll(next);
        }
    }

    private void validateNewAccount(String fullName, String email, String phone, char[] password) {
        validateProfile(fullName, email, phone);
        if (password == null || password.length < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters.");
        }
    }

    private static void validateProfile(String fullName, String email, String phone) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name is required.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (!EMAIL.matcher(email.trim().toLowerCase()).matches()) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone is required.");
        }
    }

    private void ensureEmailAvailable(String normalizedEmail, String exceptCustomerId) throws IOException {
        for (Customer c : store.readAll()) {
            if (c.getEmail() != null && c.getEmail().equalsIgnoreCase(normalizedEmail)) {
                if (exceptCustomerId == null
                        || c.getCustomerId() == null
                        || !c.getCustomerId().equalsIgnoreCase(exceptCustomerId)) {
                    throw new IllegalArgumentException("An account with this email already exists.");
                }
            }
        }
    }
}
