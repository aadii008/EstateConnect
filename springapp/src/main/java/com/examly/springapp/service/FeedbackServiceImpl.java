package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.Property;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.FeedbackRepo;
import com.examly.springapp.repository.PropertyRepo;
import com.examly.springapp.repository.UserRepo;


/**
 * Service Implementaion for managing feedback operations 
 * This class handles business logic for feedbacks and interacts with the Feedback repository
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{



    /*
     * Constructor based dependency for FeedBackRepo 
     */
    private final PropertyRepo propertyRepo;
    private final UserRepo userRepo;
    private final FeedbackRepo feedbackRepo;
    public FeedbackServiceImpl(FeedbackRepo feedbackRepo,UserRepo userRepo,PropertyRepo propertyRepo){
        this.feedbackRepo = feedbackRepo;
        this.userRepo=userRepo;
        this.propertyRepo=propertyRepo;
    }

    /*
     * Creates and saves a new Feedback entry in the database
     */
    @Override
    public Feedback createFeedback(Feedback feedback) {
        User managedUser = userRepo.findById(feedback.getUser().getUserId())
                                   .orElseThrow(() -> new RuntimeException("User not found"));
        feedback.setUser(managedUser);
    
        if (feedback.getProperty() != null) {
            Property managedProperty = propertyRepo.findById(feedback.getProperty().getPropertyId())
                                                   .orElseThrow(() -> new RuntimeException("Property not found"));
            feedback.setProperty(managedProperty);
        }
    
        return feedbackRepo.save(feedback);
    }
    
     

    /*
     * Deletes a Feedback entry by its ID if it exists. 
     */
    @Override
    public void deleteFeedback(long feedbackId) {
        if (feedbackRepo.existsById(feedbackId)){
            feedbackRepo.deleteById(feedbackId);
        }
    }

    /*
     * Retrieves all Feedback entries from the database
     */
    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepo.findAll();
    }

    /*
     * Retrieves a specific entry by its ID
     */
    @Override
    public Optional<Feedback> getFeedbackById(long feedbackId) {
        return feedbackRepo.findById(feedbackId);
    }


    /*
     * Retrieves all Feedback entries associated with a specific user ID
     */
    @Override
    public List<Feedback> getFeedbacksByUserId(long userId) {
        return feedbackRepo.findByUserUserId(userId);
    }
}
