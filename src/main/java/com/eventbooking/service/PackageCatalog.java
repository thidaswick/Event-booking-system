package com.eventbooking.service;

import com.eventbooking.model.MediaPackage;
import com.eventbooking.model.PhotographyPackage;
import com.eventbooking.model.VideographyPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builds the list of available packages. Uses polymorphism: everything is stored as MediaPackage.
 */
public final class PackageCatalog {

    private PackageCatalog() {
    }

    public static List<MediaPackage> allPackages() {
        List<MediaPackage> list = new ArrayList<>();
        list.add(new PhotographyPackage("PH-BASIC", "Essential Photos", 4, 150));
        list.add(new PhotographyPackage("PH-PREMIUM", "Premium Storytelling", 8, 350));
        list.add(new VideographyPackage("VD-HIGHLIGHT", "Highlight Reel", 5, false));
        list.add(new VideographyPackage("VD-CINEMA", "Cinematic Package", 8, true));
        return Collections.unmodifiableList(list);
    }
}
