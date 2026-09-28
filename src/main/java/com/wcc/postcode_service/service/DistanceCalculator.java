package com.wcc.postcode_service.service;

import org.springframework.stereotype.Component;

@Component // This annotation indicates that this class is a Spring component, which means it can be automatically detected and registered as a bean in the Spring application context.
// We do not need to create an object of this class. Spring will create the object and inject it into the controller class.
public class DistanceCalculator {
    private static final double EARTH_RADIUS_KM = 6371.0; // Radius of the Earth in kilometers given

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert latitude and longitude from degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Haversine formula
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c; // Distance in kilometers
    }

}
