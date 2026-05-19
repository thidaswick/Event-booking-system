package com.eventbooking.service;

import com.eventbooking.model.Contact;
import com.eventbooking.util.ContactFileStore;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactService {

    public static final String STATUS_NEW = "New";
    public static final String STATUS_IN_PROGRESS = "In Progress";
    public static final String STATUS_RESOLVED = "Resolved";

    private final ContactFileStore store;

    public ContactService(ContactFileStore store) {
        this.store = store;
    }

    public List<Contact> listAll() throws IOException {
        return store.readAll();
    }

    public Optional<Contact> findById(String contactId) throws IOException {
        if (contactId == null || contactId.isBlank()) {
            return Optional.empty();
        }
        String target = contactId.trim();
        for (Contact c : store.readAll()) {
            if (target.equalsIgnoreCase(c.getContactId())) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public List<Contact> search(String contactIdQuery, String textQuery) throws IOException {
        List<Contact> out = new ArrayList<>();
        for (Contact c : store.readAll()) {
            if (c.matchesSearch(
                    contactIdQuery == null ? "" : contactIdQuery,
                    textQuery == null ? "" : textQuery)) {
                out.add(c);
            }
        }
        return out;
    }

    public Contact createNew(Contact input) throws IOException {
        List<Contact> all = store.readAll();
        String id = store.nextContactId();
        input.setContactId(id);
        if (input.getStatus() == null || input.getStatus().isBlank()) {
            input.setStatus(STATUS_NEW);
        }
        if (input.getCreatedAt() == null || input.getCreatedAt().isBlank()) {
            input.setCreatedAt(Instant.now().toString());
        }
        all.add(input);
        store.writeAll(all);
        return input;
    }

    public void update(Contact updated) throws IOException {
        List<Contact> all = store.readAll();
        List<Contact> next = new ArrayList<>();
        boolean replaced = false;
        for (Contact c : all) {
            if (c.getContactId() != null && c.getContactId().equalsIgnoreCase(updated.getContactId())) {
                if (updated.getCreatedAt() == null || updated.getCreatedAt().isBlank()) {
                    updated.setCreatedAt(c.getCreatedAt());
                }
                next.add(updated);
                replaced = true;
            } else {
                next.add(c);
            }
        }
        if (!replaced) {
            throw new IllegalArgumentException("Contact not found: " + updated.getContactId());
        }
        store.writeAll(next);
    }

    public void deleteById(String contactId) throws IOException {
        if (contactId == null || contactId.isBlank()) {
            throw new IllegalArgumentException("contactId is required");
        }
        List<Contact> all = store.readAll();
        List<Contact> next = new ArrayList<>();
        for (Contact c : all) {
            if (c.getContactId() == null || !c.getContactId().equalsIgnoreCase(contactId.trim())) {
                next.add(c);
            }
        }
        if (next.size() == all.size()) {
            throw new IllegalArgumentException("Contact not found: " + contactId);
        }
        store.writeAll(next);
    }
}
