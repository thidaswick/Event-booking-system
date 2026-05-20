package com.eventbooking.model;

/**
 * Single studio admin credentials stored in {@code WEB-INF/data/admin.txt}.
 */
public class AdminAccount {

    private String email;
    private String saltBase64;
    private String passwordHashBase64;

    public AdminAccount() {
    }

    public AdminAccount(String email, String saltBase64, String passwordHashBase64) {
        this.email = email;
        this.saltBase64 = saltBase64;
        this.passwordHashBase64 = passwordHashBase64;
    }

    public static AdminAccount fromTsvLine(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Admin line must have 3 tab-separated fields.");
        }
        return new AdminAccount(parts[0].trim(), parts[1].trim(), parts[2].trim());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
