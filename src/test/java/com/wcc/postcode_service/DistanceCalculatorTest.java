package com.wcc.postcode_service;

import org.junit.jupiter.api.Test;

import com.wcc.postcode_service.dto.Location;
import com.wcc.postcode_service.service.DistanceCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceCalculatorTest {
    @Test
    void shouldReturnZeroForSameLocation() {
        Location location = new Location("SW1A 1AA", 51.5014, -0.1419);

        // This is a unit test, so we are not starting the Spring context.
        // We create DistanceCalculator directly because we are testing the class itself.
        DistanceCalculator calculator = new DistanceCalculator();

        double result = calculator.calculateDistance(location.getLatitude(), location.getLongitude(), location.getLatitude(), location.getLongitude());

        assertEquals(0.0, result, 0.0001);
    }
    
    @Test
    void shouldCalculateDistanceBetweenTwoLocations() {
        Location from =
                new Location(
                        "AB10 1AB",
                        57.149590,
                        -2.096923);

        Location to =
                new Location(
                        "SW1A 1AA",
                        51.501010,
                        -0.141563);

        DistanceCalculator calculator =
                new DistanceCalculator();

        double result =
                calculator.calculateDistance(
                        from.getLatitude(),
                        from.getLongitude(),
                        to.getLatitude(),
                        to.getLongitude());

        assertEquals(640.6945, result, 0.001);
    }
}
