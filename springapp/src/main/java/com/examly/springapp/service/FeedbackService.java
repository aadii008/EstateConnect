package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Feedback;


public interface FeedbackService {
    
    Feedback createFeedback(Feedback feedback);
    Optional<Feedback> getFeedbackById(long feedbackId);
    List<Feedback> getAllFeedbacks();
    void deleteFeedback(long feedbackId);
    List<Feedback> getFeedbacksByUserId(long userId);
}
