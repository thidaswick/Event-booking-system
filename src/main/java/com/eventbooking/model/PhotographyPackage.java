package com.eventbooking.model;

/**
 * Concrete package type for photography (inheritance from MediaPackage).
 */
public class PhotographyPackage extends MediaPackage {

    private final int hoursOnSite;
    private final int editedPhotos;

    public PhotographyPackage(String code, String title, int hoursOnSite, int editedPhotos) {
        super(code, title);
        this.hoursOnSite = hoursOnSite;
        this.editedPhotos = editedPhotos;
    }

    @Override
    public String getSummary() {
        return "On-site coverage: " + hoursOnSite + "h, edited stills: " + editedPhotos;
    }

    @Override
    public String getDeliverables() {
        return "Online gallery + print-ready exports";
    }

    @Override
    public String getPriceLabel() {
        return "From LKR 45,000 (sample)";
    }

    public int getHoursOnSite() {
        return hoursOnSite;
    }

    public int getEditedPhotos() {
        return editedPhotos;
    }
}
