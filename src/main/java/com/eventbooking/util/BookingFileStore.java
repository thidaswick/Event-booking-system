package com.eventbooking.util;

import com.eventbooking.model.Booking;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads and writes all bookings to a single text file (file handling for the assignment).
 * Each non-comment line is one Booking in TSV format.
 */
public class BookingFileStore {

    private static final Pattern BK_NUM = Pattern.compile("^BK(\\d+)$", Pattern.CASE_INSENSITIVE);

    private final Path dataFile;

    public BookingFileStore(Path dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * Reads every booking from disk. Returns an empty list if the file does not exist yet.
     */
    public synchronized List<Booking> readAll() throws IOException {
        if (!Files.exists(dataFile)) {
            return new ArrayList<>();
        }
        List<String> lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        List<Booking> bookings = new ArrayList<>();
        for (String raw : lines) {
            String line = raw == null ? "" : raw.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            try {
                bookings.add(Booking.fromTsvLine(line));
            } catch (IllegalArgumentException ignored) {
                // Skip malformed lines instead of crashing the whole app.
            }
        }
        return bookings;
    }

    /**
     * Overwrites the file with the given list (simple approach, easy to explain in viva).
     */
    public synchronized void writeAll(List<Booking> bookings) throws IOException {
        Path parent = dataFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        List<String> lines = new ArrayList<>();
        lines.add("# Tab-separated: bookingId, customerName, phone, eventType, eventDate, location, serviceType, packageName, specialNotes");
        for (Booking b : bookings) {
            lines.add(b.toTsvLine());
        }
        Files.write(dataFile, lines, StandardCharsets.UTF_8);
    }

    /**
     * Generates the next id in the form BK001, BK002, ... based on existing rows.
     */
    public synchronized String nextBookingId() throws IOException {
        int max = 0;
        for (Booking b : readAll()) {
            String id = b.getBookingId();
            if (id == null) {
                continue;
            }
            Matcher m = BK_NUM.matcher(id.trim());
            if (m.matches()) {
                max = Math.max(max, Integer.parseInt(m.group(1)));
            }
        }
        return String.format(Locale.ROOT, "BK%03d", max + 1);
    }
}
