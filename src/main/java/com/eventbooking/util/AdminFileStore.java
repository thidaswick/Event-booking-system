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
        ensureDefaultAdminFile();
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

    private void ensureDefaultAdminFile() throws IOException {
        if (Files.exists(dataFile)) {
            return;
        }
        Path parent = dataFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        String defaultLine = "admin@lenscraft.studio"
                + "\t3IYOAqvJTh4pErxVwoagLA==\tvaFm5Afqlo52A2ftRRhFDjodOPct+W73Mvn1vpW3Q7c=";
        Files.writeString(dataFile,
                "# Tab-separated: email, saltB64, passwordHashB64 (single studio admin account)\n"
                        + defaultLine + "\n",
                StandardCharsets.UTF_8);
    }
}
