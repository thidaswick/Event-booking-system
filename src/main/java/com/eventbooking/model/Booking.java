package com.eventbooking.model;

import java.util.Objects;

/**
 * Represents one customer booking (encapsulation: fields are private, access via getters/setters).
 * Stored as one line in a text file using tab-separated values (TSV).
 */
public class Booking {

    private String bookingId;
    private String customerName;
    private String phone;
    private String eventType;
    private String eventDate;   // yyyy-MM-dd as plain text
    private String location;
    private String serviceType; // e.g. Photography, Videography, Both
    private String packageName;
    private String specialNotes;

    public Booking() {
        // default constructor for forms / frameworks
    }

    public Booking(String bookingId, String customerName, String phone, String eventType,
                   String eventDate, String location, String serviceType, String packageName,
                   String specialNotes) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.phone = phone;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.location = location;
        this.serviceType = serviceType;
        this.packageName = packageName;
        this.specialNotes = specialNotes;
    }

    /**
     * Parses one TSV line into a Booking. Tabs inside user text are replaced at input time.
     */
    public static Booking fromTsvLine(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length < 9) {
            throw new IllegalArgumentException("Line must have 9 tab-separated fields.");
        }
        return new Booking(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim(),
                parts[6].trim(),
                parts[7].trim(),
                parts[8].trim()
        );
    }

    /**
     * Serializes this booking to one TSV line for file storage.
     */
    public String toTsvLine() {
        return String.join("\t",
                safe(bookingId),
                safe(customerName),
                safe(phone),
                safe(eventType),
                safe(eventDate),
                safe(location),
                safe(serviceType),
                safe(packageName),
                safe(specialNotes)
        );
    }

    private static String safe(String value) {
        if (value == null) {
            return "";
        }
        // Tabs would break TSV columns — strip them if any slip through.
        return value.replace("\t", " ");
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    /**
     * True if this booking matches search by id (exact, case-insensitive) or customer name (contains, case-insensitive).
     */
    public boolean matchesSearch(String bookingIdQuery, String customerNameQuery) {
        boolean idMatch = bookingIdQuery != null && !bookingIdQuery.isBlank()
                && bookingId != null && bookingId.equalsIgnoreCase(bookingIdQuery.trim());
        boolean nameMatch = customerNameQuery != null && !customerNameQuery.isBlank()
                && customerName != null
                && customerName.toLowerCase().contains(customerNameQuery.trim().toLowerCase());
        return idMatch || nameMatch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking other)) {
            return false;
        }
        return Objects.equals(bookingId, other.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookingId);
    }
}
