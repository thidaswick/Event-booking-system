package com.eventbooking.model;

import java.util.Objects;

/**
 * A sellable media package shown to customers (TSV file storage).
 */
public class StudioPackage {

    private String packageId;
    private String code;
    private String title;
    private String priceLabel;
    private String summary;
    private String deliverables;

    public StudioPackage() {
    }

    public StudioPackage(String packageId, String code, String title, String priceLabel,
                         String summary, String deliverables) {
        this.packageId = packageId;
        this.code = code;
        this.title = title;
        this.priceLabel = priceLabel;
        this.summary = summary;
        this.deliverables = deliverables;
    }

    public static StudioPackage fromTsvLine(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length < 6) {
            throw new IllegalArgumentException("Line must have 6 tab-separated fields.");
        }
        return new StudioPackage(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim()
        );
    }

    public String toTsvLine() {
        return String.join("\t",
                safe(packageId),
                safe(code),
                safe(title),
                safe(priceLabel),
                safe(summary),
                safe(deliverables)
        );
    }

    private static String safe(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\t", " ");
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel = priceLabel;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(String deliverables) {
        this.deliverables = deliverables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudioPackage other)) {
            return false;
        }
        return Objects.equals(packageId, other.packageId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(packageId);
    }
}
