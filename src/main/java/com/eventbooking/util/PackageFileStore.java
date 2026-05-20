package com.eventbooking.util;

import com.eventbooking.model.StudioPackage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageFileStore {

    private static final Pattern PKG_NUM = Pattern.compile("^PKG(\\d+)$", Pattern.CASE_INSENSITIVE);

    private final Path dataFile;

    public PackageFileStore(Path dataFile) {
        this.dataFile = dataFile;
    }

    public synchronized List<StudioPackage> readAll() throws IOException {
        if (!Files.exists(dataFile)) {
            return new ArrayList<>();
        }
        List<String> lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
        List<StudioPackage> packages = new ArrayList<>();
        for (String raw : lines) {
            String line = raw == null ? "" : raw.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            try {
                packages.add(StudioPackage.fromTsvLine(line));
            } catch (IllegalArgumentException ignored) {
                // Skip malformed lines.
            }
        }
        return packages;
    }

    public synchronized void writeAll(List<StudioPackage> packages) throws IOException {
        Path parent = dataFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        List<String> lines = new ArrayList<>();
        lines.add("# Tab-separated: packageId, code, title, priceLabel, summary, deliverables");
        for (StudioPackage p : packages) {
            lines.add(p.toTsvLine());
        }
        Files.write(dataFile, lines, StandardCharsets.UTF_8);
    }

    public synchronized String nextPackageId() throws IOException {
        int max = 0;
        for (StudioPackage p : readAll()) {
            String id = p.getPackageId();
            if (id == null) {
                continue;
            }
            Matcher m = PKG_NUM.matcher(id.trim());
            if (m.matches()) {
                max = Math.max(max, Integer.parseInt(m.group(1)));
            }
        }
        return String.format(Locale.ROOT, "PKG%03d", max + 1);
    }
}
