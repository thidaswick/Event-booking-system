package com.eventbooking.util;

import com.eventbooking.model.Contact;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactFileStore {

    private static final Pattern CT_NUM = Pattern.compile("^CT(\\d+)$", Pattern.CASE_INSENSITIVE);

    private final Path dataFile;

    public ContactFileStore(Path dataFile) {
        this.dataFile = dataFile;
    }

    public synchronized List<Contact> readAll() throws IOException {
        if (!Files.exists(dataFile)) {
            return new ArrayList<>();
        }
        List<String> lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        List<Contact> contacts = new ArrayList<>();
        for (String raw : lines) {
            String line = raw == null ? "" : raw.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            try {
                contacts.add(Contact.fromTsvLine(line));
            } catch (IllegalArgumentException ignored) {
                // Skip malformed lines.
            }
        }
        return contacts;
    }

    public synchronized void writeAll(List<Contact> contacts) throws IOException {
        Path parent = dataFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        List<String> lines = new ArrayList<>();
        lines.add("# Tab-separated: contactId, fullName, email, phone, subject, message, status, createdAt");
        for (Contact c : contacts) {
            lines.add(c.toTsvLine());
        }
        Files.write(dataFile, lines, StandardCharsets.UTF_8);
    }

    public synchronized String nextContactId() throws IOException {
        int max = 0;
        for (Contact c : readAll()) {
            String id = c.getContactId();
            if (id == null) {
                continue;
            }
            Matcher m = CT_NUM.matcher(id.trim());
            if (m.matches()) {
                max = Math.max(max, Integer.parseInt(m.group(1)));
            }
        }
        return String.format(Locale.ROOT, "CT%03d", max + 1);
    }
}
