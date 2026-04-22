package com.eventbooking.model;

/**
 * Base type for sellable packages (inheritance + polymorphism).
 * Subclasses override behaviour while sharing a common interface for the UI layer.
 */
public abstract class MediaPackage {

    protected final String code;
    protected final String title;

    protected MediaPackage(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Short human-readable summary (polymorphic: each subclass provides its own text).
     */
    public abstract String getSummary();

    /**
     * Typical deliverables for this package type.
     */
    public abstract String getDeliverables();

    /**
     * Display price as text (kept as String to stay simple for the assignment).
     */
    public abstract String getPriceLabel();
}
