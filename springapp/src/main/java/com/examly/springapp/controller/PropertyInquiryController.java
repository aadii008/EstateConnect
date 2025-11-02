package com.examly.springapp.controller;

import java.util.List;
import java.util.Map;
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

import com.examly.springapp.model.PropertyInquiry;
import com.examly.springapp.service.PropertyInquiryService;

/*
 * REST Controller for managing Property Inquiry-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting property inquiries.
 */
@RestController
@RequestMapping("/api/inquiries")
public class PropertyInquiryController {

    private final PropertyInquiryService propertyInquiryService;

    /*
     * Constructor-based injection of PropertyInquiryService.
     */
    public PropertyInquiryController(PropertyInquiryService propertyInquiryService) {
        this.propertyInquiryService = propertyInquiryService;
    }

    /*
     * Endpoint to create a new Property Inquiry.
     * Returns HTTP 201 with the created inquiry if successful or HTTP 409 if the creation fails.
     */
    @PostMapping
    ResponseEntity<PropertyInquiry> addInquiry(@RequestBody PropertyInquiry propertyInquiry) {
        PropertyInquiry savePropertyInquiry = propertyInquiryService.addInquiry(propertyInquiry);
        if (savePropertyInquiry != null) {
            return ResponseEntity.status(201).body(savePropertyInquiry);
        }
        return ResponseEntity.status(403).build();
    }

    /*
     * Endpoint to retrieve a Property Inquiry by its ID.
     * Returns HTTP 200 with the inquiry if found or HTTP 404 if not found.
     */
    @GetMapping("/{inquiryId}")
    ResponseEntity<Optional<PropertyInquiry>> getInquiryById(@PathVariable long inquiryId) {
        Optional<PropertyInquiry> savedPropertyInquiry = propertyInquiryService.getInquiryById(inquiryId);
        if (savedPropertyInquiry.isPresent()) {
            return ResponseEntity.status(200).body(savedPropertyInquiry);
        }
        return ResponseEntity.status(404).build();
    }

    /*
     * Endpoint to retrieve all Property Inquiries.
     * Returns HTTP 200 with the list of inquiries if any exist or HTTP 400 if the list is empty.
     */
    @GetMapping
    ResponseEntity<List<PropertyInquiry>> getAllInquiries() {
        List<PropertyInquiry> savedPropertyInquiries = propertyInquiryService.getAllInquiries();
        if (savedPropertyInquiries!=null) {
            return ResponseEntity.status(200).body(savedPropertyInquiries);
        }
        return ResponseEntity.status(400).build();
    }

    /*
     * Endpoint to update an existing Property Inquiry by its ID.
     * Returns HTTP 200 with the updated inquiry if successful or HTTP 404 if the inquiry does not exist.
     */
    @PutMapping("/{inquiryId}")
    ResponseEntity<PropertyInquiry> updateInquiry(@PathVariable long inquiryId, @RequestBody PropertyInquiry propertyInquiry) {
        PropertyInquiry updatedPropertyInquiry = propertyInquiryService.updateInquiry(inquiryId, propertyInquiry);
        if (updatedPropertyInquiry != null) {
            return ResponseEntity.status(200).body(updatedPropertyInquiry);
        }
        return ResponseEntity.status(404).build();
    }

    /*
     * Endpoint to delete a Property Inquiry by its ID.
     * Returns HTTP 200 if deletion is successful or HTTP 404 if the inquiry does not exist.
     */
    @DeleteMapping("/{inquiryId}")
    ResponseEntity<String> deleteInquiry(@PathVariable long inquiryId) {
        Optional<PropertyInquiry> propertyInquiry = propertyInquiryService.getInquiryById(inquiryId);
        if (propertyInquiry.isPresent()) {
            propertyInquiryService.deleteInquiry(inquiryId);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).body("Inquiry not found.");
    }

    /*
     * Endpoint to retrieve all Property Inquiries for a specific user by user ID.
     * Returns HTTP 200 with the list of inquiries if found or HTTP 404 if no inquiries are associated with the user.
     */
    @GetMapping("/user/{userId}")
    ResponseEntity<List<PropertyInquiry>> getInquiriesByUserId(@PathVariable long userId) {
        List<PropertyInquiry> propertyInquiries = propertyInquiryService.getInquiriesByUserId(userId);
        if (!propertyInquiries.isEmpty()) {
            return ResponseEntity.status(200).body(propertyInquiries);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("adminControlPanel")
    ResponseEntity<Map<String, Integer>> getAdminControlPanelData(){
        Map<String, Integer> data = propertyInquiryService.getAdminControlPanelData();

        return ResponseEntity.status(200).body(data);
    }
}
