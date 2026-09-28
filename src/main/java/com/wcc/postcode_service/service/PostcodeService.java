package com.wcc.postcode_service.service;

import org.springframework.stereotype.Service;

import com.wcc.postcode_service.dto.Location;
import com.wcc.postcode_service.dto.UpdatePostcodeRequest;
import com.wcc.postcode_service.entity.Postcode;
import com.wcc.postcode_service.exception.PostcodeNotFoundException;
import com.wcc.postcode_service.repository.PostcodeRepository;

@Service // business logic - doesn't need to create object of this class. Spring will create the object and inject it into the controller class.
public class PostcodeService {
    private final PostcodeRepository postcodeRepository;

    public PostcodeService(PostcodeRepository postcodeRepository) {
        this.postcodeRepository = postcodeRepository;
    }

    public Location getLocationByPostcode(String postcode) {

        String normalizedPostcode = postcode.trim().toUpperCase(); // Remove spaces and convert to uppercase

        Postcode result = postcodeRepository.findByPostcode(normalizedPostcode)
                .orElseThrow(() -> new PostcodeNotFoundException(normalizedPostcode));

        return new Location(result.getPostcode(), result.getLatitude(), result.getLongitude());
    }

    // Update the latitude and longitude of a postcode in the database
    public Location updatePostcode(String postcode, UpdatePostcodeRequest request) {

        String normalizedPostcode = postcode.trim().toUpperCase();

        Postcode result = postcodeRepository
                .findByPostcode(normalizedPostcode)
                .orElseThrow(() ->
                        new PostcodeNotFoundException(
                                normalizedPostcode));

        result.setLatitude(request.getLatitude());
        result.setLongitude(request.getLongitude());

        Postcode updated = postcodeRepository.save(result);

        return new Location(
                updated.getPostcode(),
                updated.getLatitude(),
                updated.getLongitude());
    }
}
