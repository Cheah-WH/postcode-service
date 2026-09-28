package com.wcc.postcode_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wcc.postcode_service.entity.Postcode;

import java.util.Optional;

// DB access layer. Define the methods to access the database. Spring Data JPA will automatically implement this interface for us.
public interface PostcodeRepository extends JpaRepository<Postcode, Long> { // Create repository for the Postcode entity, primary key is of type Long. JpaRepository provides basic CRUD operations and pagination support.

    /* Extending JpaRepository provides methods such as:
     * findAll()
     * findById()
     * save()
     * delete()
     * deleteById()
     * count()
     * existsById()
    */
    Optional<Postcode> findByPostcode(String postcode);
}