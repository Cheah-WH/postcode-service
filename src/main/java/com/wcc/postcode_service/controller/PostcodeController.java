package com.wcc.postcode_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcc.postcode_service.dto.DistanceResponse;
import com.wcc.postcode_service.dto.Location;
import com.wcc.postcode_service.dto.UpdatePostcodeRequest;
import com.wcc.postcode_service.exception.PostcodeNotFoundException;
import com.wcc.postcode_service.service.DistanceCalculator;
import com.wcc.postcode_service.service.PostcodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;



@RestController // This annotation indicates that this class is a REST controller, which means it handles HTTP requests and return responses.
public class PostcodeController {

    private final PostcodeService postcodeService;
    private final DistanceCalculator distanceCalculator;
    private static final Logger logger = LoggerFactory.getLogger(PostcodeController.class);

    public PostcodeController(PostcodeService postcodeService, DistanceCalculator distanceCalculator) {
        this.postcodeService = postcodeService;
        this.distanceCalculator = distanceCalculator;
    }

    @GetMapping("/distance") // Endpoint for GET requests to /distance.
    public DistanceResponse distance(@RequestParam String from, @RequestParam String to) {
        // This method takes two query parameters, 'from' and 'to', and returns a string indicating the distance between them.

        try {
            // Location fromLocation = new Location(from, 51.5014, -0.1419);
            // Location toLocation = new Location(to, 51.5175, -0.0969);
            Location fromLocation = postcodeService.getLocationByPostcode(from);
            Location toLocation = postcodeService.getLocationByPostcode(to);

            double distance = distanceCalculator.calculateDistance(
                fromLocation.getLatitude(),
                fromLocation.getLongitude(),
                toLocation.getLatitude(), 
                toLocation.getLongitude()
            );

            logger.atInfo()
                .addKeyValue("event", "DISTANCE_REQUEST_SUCCESS")
                .addKeyValue("function", "distance")
                .addKeyValue("params", Map.of(
                        "from", from,
                        "to", to
                ))
                .addKeyValue("distanceKm", distance)
                .log();


            return new DistanceResponse(fromLocation, toLocation, distance, "km");
        } catch (PostcodeNotFoundException e) {
            logger.atWarn()
                .addKeyValue("event", "DISTANCE_REQUEST_FAILED")
                .addKeyValue("function", "distance")
                .addKeyValue("params", Map.of(
                        "from", from,
                        "to", to
                ))
                .addKeyValue("error", e.getMessage())
                .log();
            throw e; // Re-throw the exception to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            logger.atError()
                .addKeyValue("event", "DISTANCE_REQUEST_FAILED")
                .addKeyValue("function", "distance")
                .addKeyValue("params", Map.of(
                        "from", from,
                        "to", to
                ))
                .addKeyValue("error", e.getMessage())
                .setCause(e)
                .log();
            throw e; // Re-throw the exception to be handled by GlobalExceptionHandler
        }
    }

    @PutMapping("/postcodes/{postcode}")
    public Location updatePostcode(@PathVariable String postcode, @RequestBody UpdatePostcodeRequest request) {
        try{
            Location updated = postcodeService.updatePostcode(postcode, request);
            logger.atInfo()
                .addKeyValue("event", "UPDATE_POSTCODE_REQUEST_SUCCESS")
                .addKeyValue("function", "updatePostcode")
                .addKeyValue("params", Map.of(
                        "postcode", postcode,
                        "latitude", request.getLatitude(),
                        "longitude", request.getLongitude()
                ))
                .log();

            return updated;
        } catch (PostcodeNotFoundException e) {
            logger.atWarn()
                .addKeyValue("event", "UPDATE_POSTCODE_REQUEST_FAILED")
                .addKeyValue("function", "updatePostcode")
                .addKeyValue("params", Map.of(
                        "postcode", postcode,
                        "latitude", request.getLatitude(),
                        "longitude", request.getLongitude()
                ))
                .addKeyValue("error", e.getMessage())
                .log();
            throw e; // Re-throw the exception to be handled by GlobalExceptionHandler
        } catch (Exception e) {
            logger.atError()
                .addKeyValue("event", "UPDATE_POSTCODE_REQUEST_FAILED")
                .addKeyValue("function", "updatePostcode")
                .addKeyValue("params", Map.of(
                        "postcode", postcode,
                        "latitude", request.getLatitude(),
                        "longitude", request.getLongitude()
                ))
                .addKeyValue("error", e.getMessage())
                .setCause(e)
                .log();
            throw e; // Re-throw the exception to be handled by GlobalExceptionHandler
        }
        
    }
}