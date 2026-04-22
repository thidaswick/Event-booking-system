package com.eventbooking.service;

import com.eventbooking.model.Booking;
import com.eventbooking.util.BookingFileStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Small service layer on top of BookingFileStore (keeps servlets thin and logic in one place).
 */
public class BookingService {

    private final BookingFileStore store;

    public BookingService(BookingFileStore store) {
        this.store = store;
    }

    public List<Booking> listAll() throws IOException {
        return store.readAll();
    }

    public Optional<Booking> findById(String bookingId) throws IOException {
        if (bookingId == null || bookingId.isBlank()) {
            return Optional.empty();
        }
        String target = bookingId.trim();
        for (Booking b : store.readAll()) {
            if (target.equalsIgnoreCase(b.getBookingId())) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    public List<Booking> search(String bookingIdQuery, String customerNameQuery) throws IOException {
        List<Booking> out = new ArrayList<>();
        for (Booking b : store.readAll()) {
            if (b.matchesSearch(
                    bookingIdQuery == null ? "" : bookingIdQuery,
                    customerNameQuery == null ? "" : customerNameQuery)) {
                out.add(b);
            }
        }
        return out;
    }

    public Booking createNew(Booking input) throws IOException {
        List<Booking> all = store.readAll();
        String id = store.nextBookingId();
        input.setBookingId(id);
        all.add(input);
        store.writeAll(all);
        return input;
    }

    public void update(Booking updated) throws IOException {
        List<Booking> all = store.readAll();
        List<Booking> next = new ArrayList<>();
        boolean replaced = false;
        for (Booking b : all) {
            if (b.getBookingId() != null && b.getBookingId().equalsIgnoreCase(updated.getBookingId())) {
                next.add(updated);
                replaced = true;
            } else {
                next.add(b);
            }
        }
        if (!replaced) {
            throw new IllegalArgumentException("Booking not found: " + updated.getBookingId());
        }
        store.writeAll(next);
    }

    public void deleteById(String bookingId) throws IOException {
        if (bookingId == null || bookingId.isBlank()) {
            throw new IllegalArgumentException("bookingId is required");
        }
        List<Booking> all = store.readAll();
        List<Booking> next = new ArrayList<>();
        for (Booking b : all) {
            if (b.getBookingId() == null || !b.getBookingId().equalsIgnoreCase(bookingId.trim())) {
                next.add(b);
            }
        }
        if (next.size() == all.size()) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }
        store.writeAll(next);
    }
}
