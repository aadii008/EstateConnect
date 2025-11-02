package com.examly.springapp.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.examly.springapp.model.PropertyInquiry;

/*
 * Service interface for managing Property Inquiry operations.
 */
public interface PropertyInquiryService {

    /*
     * Adds a new Property Inquiry.
     */
    PropertyInquiry addInquiry(PropertyInquiry propertyInquiry);

    /*
     * Retrieves a Property Inquiry by its ID.
     */
    Optional<PropertyInquiry> getInquiryById(long inquiryId);

    /*
     * Retrieves all Property Inquiries.
     */
    List<PropertyInquiry> getAllInquiries();

    /*
     * Updates an existing Property Inquiry.
     */
    PropertyInquiry updateInquiry(long inquiryId, PropertyInquiry propertyInquiry);

    /*
     * Deletes a Property Inquiry by its ID.
     */
    void deleteInquiry(long inquiryId);

    /*
     * Retrieves all Property Inquiries associated with a specific user.
     */
    List<PropertyInquiry> getInquiriesByUserId(long userId);

    Map<String, Integer> getAdminControlPanelData();
}