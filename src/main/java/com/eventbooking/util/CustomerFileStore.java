package com.eventbooking.util;

import com.eventbooking.model.Customer;

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
 * File-backed store for registered customers (TSV, same pattern as {@link BookingFileStore}).
 */
public class CustomerFileStore {

    private static final Pattern CU_NUM = Pattern.compile("^CU(\\d+)$", Pattern.CASE_INSENSITIVE);

    private final Path dataFile;

    public CustomerFileStore(Path dataFile) {
        this.dataFile = dataFile;
    }

    public synchronized List<Customer> readAll() throws IOException {
        if (!Files.exists(dataFile)) {
            return new ArrayList<>();
        }
        List<String> lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        List<Customer> customers = new ArrayList<>();
        for (String raw : lines) {
            String line = raw == null ? "" : raw.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            try {
                customers.add(Customer.fromTsvLine(line));
            } catch (IllegalArgumentException ignored) {
                // skip bad lines
            }
        }
        return customers;
    }

    public synchronized void writeAll(List<Customer> customers) throws IOException {
        Path parent = dataFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        List<String> lines = new ArrayList<>();
        lines.add("# Tab-separated: customerId, fullName, email, phone, saltB64, passwordHashB64, createdAt");
        for (Customer c : customers) {
            lines.add(c.toTsvLine());
        }
        Files.write(dataFile, lines, StandardCharsets.UTF_8);
    }

    public synchronized String nextCustomerId() throws IOException {
        int max = 0;
        for (Customer c : readAll()) {
            String id = c.getCustomerId();
            if (id == null) {
                continue;
            }
            Matcher m = CU_NUM.matcher(id.trim());
            if (m.matches()) {
                max = Math.max(max, Integer.parseInt(m.group(1)));
            }
        }
        return String.format(Locale.ROOT, "CU%03d", max + 1);
    }
}
