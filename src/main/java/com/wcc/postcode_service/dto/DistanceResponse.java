package com.wcc.postcode_service.dto;

public class DistanceResponse {
    private Location from;
    private Location to;
    private double distance;
    private String unit;

    public DistanceResponse(Location from, Location to, double distance, String unit) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.unit = unit;
    }

    // Getters are required to access private fields from outside the class
    // If object is serialized to JSON, the getters are automatically used.
    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }

    public String getUnit() {
        return unit;
    }
}