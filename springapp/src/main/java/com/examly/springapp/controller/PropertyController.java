package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Property;
import com.examly.springapp.service.PropertyService;

// Marks this class as a REST controller that handles HTTP requests
@RestController

// Base URL for all endpoints in this controller
@RequestMapping("/api/properties")
public class PropertyController {

    // Injects the PropertyService dependency
    private final PropertyService propertyService;

    // Constructor-based dependency injectio
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /**
     * Adds a new property to the system.
     * Endpoint: POST /api/properties
     * Request Body: Property object
     * Response: 201 Created with the saved property, or 409 Conflict if failed
     */
    @PostMapping
    ResponseEntity<Property> addProperty(@RequestBody Property property){

        Property saveProperty=propertyService.addProperty(property);
        if(saveProperty!=null){
            return ResponseEntity.status(201).body(saveProperty);
        }
        else{
            return ResponseEntity.status(409).build();
        }
    }

    /**
     * Retrieves a property by its ID.
     * Endpoint: GET /api/properties/{propertyId}
     * Path Variable: propertyId
     * Response: 200 OK with property, or 404 Not Found
     */
    @GetMapping("/{propertyId}")
    ResponseEntity<Optional<Property>> getPropertyById(@PathVariable long propertyId){

        Optional<Property> property=propertyService.getPropertyById(propertyId);
        if(property.isPresent()){
            return ResponseEntity.status(200).body(property);
        }
        else{
            return ResponseEntity.status(404).build();
        }  
    }

    /**
     * Retrieves all properties.
     * Endpoint: GET /api/properties
     * Response: 200 OK with list of properties (can be empty)
     */
    @GetMapping
    ResponseEntity<List<Property>> getAllProperties(){
        List<Property> property=propertyService.getAllProperties();
        if(property!=null){
            return ResponseEntity.status(200).body(property);
        }
        else{
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * Updates an existing property.
     * Endpoint: PUT /api/properties/{propertyId}
     * Path Variable: propertyId
     * Request Body: Updated Property object
     * Response: 200 OK with updated property, or 404 Not Found
     */
    @PutMapping("/{propertyId}")
    ResponseEntity<Property> updateProperty(@PathVariable long propertyId, @RequestBody Property property) {

        Property existingProperty = propertyService.updateProperty(propertyId, property);
        if (existingProperty!=null) {
            return ResponseEntity.status(200).body(existingProperty); 
        } else {
            return ResponseEntity.status(404).build(); 
        }
    }

    /**
     * Deletes a property by its ID.
     * Endpoint: DELETE /api/properties/{propertyId}
     * Path Variable: propertyId
     * Response: 200 OK with success message, or 404 Not Found with error message
     */
    @DeleteMapping("/{propertyId}")
    ResponseEntity<String> deleteById(@PathVariable long propertyId){
        Property property = propertyService.getPropertyById(propertyId).orElse(null);
        if(property!=null){
            propertyService.deleteProperty(propertyId);
            return ResponseEntity.status(204).build();
        }else{
            return ResponseEntity.status(404).body("Cannot find a Property with this id " + propertyId);
        }
    }

}
