package com.eventbooking.model;

/**
 * Concrete package type for videography (inheritance from MediaPackage).
 */
public class VideographyPackage extends MediaPackage {

    private final int highlightMinutes;
    private final boolean includesDrone;

    public VideographyPackage(String code, String title, int highlightMinutes, boolean includesDrone) {
        super(code, title);
        this.highlightMinutes = highlightMinutes;
        this.includesDrone = includesDrone;
    }

    @Override
    public String getSummary() {
        String drone = includesDrone ? "Includes drone add-on" : "Ground cameras only";
        return "Highlight film: " + highlightMinutes + " min — " + drone;
    }

    @Override
    public String getDeliverables() {
        return "4K master + social cuts (vertical)";
    }

    @Override
    public String getPriceLabel() {
        return "From LKR 55,000 (sample)";
    }

    public int getHighlightMinutes() {
        return highlightMinutes;
    }

    public boolean isIncludesDrone() {
        return includesDrone;
    }
}
