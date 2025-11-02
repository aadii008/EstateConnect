package com.examly.springapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.exceptions.PropertyInquiryException;
import com.examly.springapp.model.Property;
import com.examly.springapp.model.PropertyInquiry;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.FeedbackRepo;
import com.examly.springapp.repository.PropertyInquiryRepo;
import com.examly.springapp.repository.PropertyRepo;
import com.examly.springapp.repository.UserRepo;

/*
 * Service implementation for managing Property Inquiry operations.
 * Provides logic for creating, retrieving, updating, and deleting inquiries.
 */
@Service
public class PropertyInquiryServiceImpl implements PropertyInquiryService {

    private final PropertyInquiryRepo propertyInquiryRepo;
    private final PropertyRepo propertyRepo;
    private final FeedbackRepo feedbackRepo;
    private final UserRepo userRepo;
    /*
     * Constructor-based injection of PropertyInquiryRepo.
     * Constructor-based injection of UserRepo.
     */
    public PropertyInquiryServiceImpl(PropertyInquiryRepo propertyInquiryRepo,UserRepo userRepo,PropertyRepo propertyRepo,  FeedbackRepo feedbackRepo) {
        this.propertyInquiryRepo = propertyInquiryRepo;
        this.userRepo=userRepo;
        this.propertyRepo=propertyRepo;
        this.feedbackRepo = feedbackRepo;
    }

    /*
     * Adds a new Property Inquiry to the database.
     */
    @Override
    public PropertyInquiry addInquiry(PropertyInquiry propertyInquiry) {
        if(propertyInquiryRepo.existsByProperty_PropertyIdAndUser_UserId(propertyInquiry.getProperty().getPropertyId(), propertyInquiry.getUser().getUserId())){
            throw new PropertyInquiryException("Inquiry Already Exists.");
        }
        User managedUser = userRepo.findById(propertyInquiry.getUser().getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        propertyInquiry.setUser(managedUser);
        if (propertyInquiry.getProperty() != null) {
        Property managedProperty = propertyRepo.findById(propertyInquiry.getProperty().getPropertyId()).orElseThrow(() -> new RuntimeException("Property not found"));
        propertyInquiry.setProperty(managedProperty);
        }
        return propertyInquiryRepo.save(propertyInquiry);
    }

    /*
     * Retrieves a Property Inquiry by its ID.
     */
    @Override
    public Optional<PropertyInquiry> getInquiryById(long inquiryId) {
        return propertyInquiryRepo.findById(inquiryId);
    }

    /*
     * Retrieves all Property Inquiries from the database.
     */
    @Override
    public List<PropertyInquiry> getAllInquiries() {
        return propertyInquiryRepo.findAll();
    }

    /*
     * Updates an existing Property Inquiry.
     */
    @Override
    public PropertyInquiry updateInquiry(long inquiryId, PropertyInquiry propertyInquiry) {
        Optional<PropertyInquiry> savedPropertyInquiry = propertyInquiryRepo.findById(inquiryId);

        if (savedPropertyInquiry.isPresent()) {
            PropertyInquiry existingInquiry = savedPropertyInquiry.get();

            existingInquiry.setAdminResponse(propertyInquiry.getAdminResponse());
            existingInquiry.setStatus(propertyInquiry.getStatus());

            return propertyInquiryRepo.save(existingInquiry);
        }

        return null;
    }

    /*
     * Deletes a Property Inquiry by its ID.
     */
    @Override
    public void deleteInquiry(long inquiryId) {
        if (propertyInquiryRepo.existsById(inquiryId)) {
            propertyInquiryRepo.deleteById(inquiryId);
        }
    }

    /*
     * Retrieves all Property Inquiries associated with a specific user.
     */
    @Override
    public List<PropertyInquiry> getInquiriesByUserId(long userId) {
        return propertyInquiryRepo.findAllByUserUserId(userId);
    }

    public Map<String, Integer> getAdminControlPanelData(){

        Map<String, Integer> map = new HashMap<>();

        map.put("totalInquiries", getAllInquiries().size());
        map.put("totalFeedback", feedbackRepo.findAll().size());
        map.put("totalProperties", propertyRepo.findAll().size());
        map.put("highPriorityInquiries", propertyInquiryRepo.findAllByPriority("High").size());
        map.put("unresolvedInquiries", propertyInquiryRepo.findAllByStatus("Pending").size());

        return map;
    }

    
}
