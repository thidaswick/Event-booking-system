package com.eventbooking.util;

import com.eventbooking.model.AdminAccount;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class AdminFileStore {

    private final Path dataFile;

    public AdminFileStore(Path dataFile) {
        this.dataFile = dataFile;
    }

    public synchronized Optional<AdminAccount> readAdmin() throws IOException {
        if (!Files.exists(dataFile)) {
            return Optional.empty();
        }
        for (String raw : Files.readAllLines(dataFile, StandardCharsets.UTF_8)) {
            String line = raw == null ? "" : raw.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            try {
                return Optional.of(AdminAccount.fromTsvLine(line));
            } catch (IllegalArgumentException ignored) {
                // skip bad line
            }
        }
        return Optional.empty();
    }
}
