package com.eventbooking.model;

import java.util.Objects;

/**
 * Registered studio customer (separate from a one-off {@link Booking}).
 * Persisted as one TSV line in WEB-INF/data/customers.txt.
 */
public class Customer {

    private String customerId;
    private String fullName;
    private String email;
    private String phone;
    /** Base64-encoded salt for PBKDF2 */
    private String saltBase64;
    /** Base64-encoded derived key bytes */
    private String passwordHashBase64;
    /** ISO-8601 instant string */
    private String createdAt;

    public Customer() {
    }

    public Customer(String customerId, String fullName, String email, String phone,
                    String saltBase64, String passwordHashBase64, String createdAt) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.saltBase64 = saltBase64;
        this.passwordHashBase64 = passwordHashBase64;
        this.createdAt = createdAt;
    }

    public static Customer fromTsvLine(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length < 7) {
            throw new IllegalArgumentException("Line must have 7 tab-separated fields.");
        }
        return new Customer(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim(),
                parts[6].trim()
        );
    }

    public String toTsvLine() {
        return String.join("\t",
                safe(customerId),
                safe(fullName),
                safe(email),
                safe(phone),
                safe(saltBase64),
                safe(passwordHashBase64),
                safe(createdAt)
        );
    }

    private static String safe(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\t", " ");
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSaltBase64() {
        return saltBase64;
    }

    public void setSaltBase64(String saltBase64) {
        this.saltBase64 = saltBase64;
    }

    public String getPasswordHashBase64() {
        return passwordHashBase64;
    }

    public void setPasswordHashBase64(String passwordHashBase64) {
        this.passwordHashBase64 = passwordHashBase64;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer other)) {
            return false;
        }
        return Objects.equals(customerId, other.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customerId);
    }
}
