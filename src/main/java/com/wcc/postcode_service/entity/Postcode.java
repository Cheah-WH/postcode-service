package com.wcc.postcode_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "postcodes")
public class Postcode {

    @Id // Primary key
    private Long id;

    @Column(nullable = false, unique = true)
    private String postcode;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    protected Postcode() {
        // Required by JPA
    }

    public Postcode(Long id, String postcode, double latitude, double longitude) {
        this.id = id;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}