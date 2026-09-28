package com.wcc.postcode_service.exception;

public class PostcodeNotFoundException extends RuntimeException {

    public PostcodeNotFoundException(String postcode) {
        super("Postcode not found: " + postcode);
    }
    
}
