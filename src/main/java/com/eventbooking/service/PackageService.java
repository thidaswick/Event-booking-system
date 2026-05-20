package com.eventbooking.service;

import com.eventbooking.model.StudioPackage;
import com.eventbooking.util.PackageFileStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PackageService {

    private final PackageFileStore store;

    public PackageService(PackageFileStore store) {
        this.store = store;
    }

    public List<StudioPackage> listAll() throws IOException {
        ensureDefaults();
        return store.readAll();
    }

    public Optional<StudioPackage> findById(String packageId) throws IOException {
        if (packageId == null || packageId.isBlank()) {
            return Optional.empty();
        }
        String target = packageId.trim();
        for (StudioPackage p : listAll()) {
            if (target.equalsIgnoreCase(p.getPackageId())) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public StudioPackage createNew(StudioPackage input) throws IOException {
        List<StudioPackage> all = listAll();
        String id = store.nextPackageId();
        input.setPackageId(id);
        all.add(input);
        store.writeAll(all);
        return input;
    }

    public void update(StudioPackage updated) throws IOException {
        List<StudioPackage> all = listAll();
        List<StudioPackage> next = new ArrayList<>();
        boolean replaced = false;
        for (StudioPackage p : all) {
            if (p.getPackageId() != null && p.getPackageId().equalsIgnoreCase(updated.getPackageId())) {
                next.add(updated);
                replaced = true;
            } else {
                next.add(p);
            }
        }
        if (!replaced) {
            throw new IllegalArgumentException("Package not found: " + updated.getPackageId());
        }
        store.writeAll(next);
    }

    public void deleteById(String packageId) throws IOException {
        if (packageId == null || packageId.isBlank()) {
            throw new IllegalArgumentException("Package id is required.");
        }
        String target = packageId.trim();
        List<StudioPackage> all = listAll();
        List<StudioPackage> next = new ArrayList<>();
        boolean removed = false;
        for (StudioPackage p : all) {
            if (p.getPackageId() != null && p.getPackageId().equalsIgnoreCase(target)) {
                removed = true;
            } else {
                next.add(p);
            }
        }
        if (!removed) {
            throw new IllegalArgumentException("Package not found: " + target);
        }
        store.writeAll(next);
    }

    private synchronized void ensureDefaults() throws IOException {
        if (!store.readAll().isEmpty()) {
            return;
        }
        List<StudioPackage> defaults = new ArrayList<>();
        defaults.add(new StudioPackage("PKG001", "PH-BASIC", "Essential Photos",
                "From LKR 45,000 (sample)",
                "On-site coverage: 4h, edited stills: 150",
                "Online gallery + print-ready exports"));
        defaults.add(new StudioPackage("PKG002", "PH-PREMIUM", "Premium Storytelling",
                "From LKR 85,000 (sample)",
                "On-site coverage: 8h, edited stills: 350",
                "Online gallery + print-ready exports + premium album layout"));
        defaults.add(new StudioPackage("PKG003", "VD-HIGHLIGHT", "Highlight Reel",
                "From LKR 55,000 (sample)",
                "Highlight film: 5 min — Ground cameras only",
                "4K master + social cuts (vertical)"));
        defaults.add(new StudioPackage("PKG004", "VD-CINEMA", "Cinematic Package",
                "From LKR 120,000 (sample)",
                "Highlight film: 8 min — Includes drone add-on",
                "4K master + social cuts (vertical)"));
        store.writeAll(defaults);
    }
}
