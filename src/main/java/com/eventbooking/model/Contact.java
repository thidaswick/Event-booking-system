package com.eventbooking.model;

import java.util.Objects;

/**
 * A contact inquiry submitted via the Contact Us page (TSV file storage).
 */
public class Contact {

    private String contactId;
    private String fullName;
    private String email;
    private String phone;
    private String subject;
    private String message;
    private String status;
    private String createdAt;

    public Contact() {
    }

    public Contact(String contactId, String fullName, String email, String phone,
                   String subject, String message, String status, String createdAt) {
        this.contactId = contactId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Contact fromTsvLine(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length < 8) {
            throw new IllegalArgumentException("Line must have 8 tab-separated fields.");
        }
        return new Contact(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim(),
                parts[6].trim(),
                parts[7].trim()
        );
    }

    public String toTsvLine() {
        return String.join("\t",
                safe(contactId),
                safe(fullName),
                safe(email),
                safe(phone),
                safe(subject),
                safe(message),
                safe(status),
                safe(createdAt)
        );
    }

    private static String safe(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\t", " ");
    }

    public boolean matchesSearch(String contactIdQuery, String nameOrEmailQuery) {
        boolean idMatch = contactIdQuery != null && !contactIdQuery.isBlank()
                && contactId != null && contactId.equalsIgnoreCase(contactIdQuery.trim());
        if (idMatch) {
            return true;
        }
        if (nameOrEmailQuery == null || nameOrEmailQuery.isBlank()) {
            return false;
        }
        String q = nameOrEmailQuery.trim().toLowerCase();
        boolean nameMatch = fullName != null && fullName.toLowerCase().contains(q);
        boolean emailMatch = email != null && email.toLowerCase().contains(q);
        boolean subjectMatch = subject != null && subject.toLowerCase().contains(q);
        return nameMatch || emailMatch || subjectMatch;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof Contact other)) {
            return false;
        }
        return Objects.equals(contactId, other.contactId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contactId);
    }
}
