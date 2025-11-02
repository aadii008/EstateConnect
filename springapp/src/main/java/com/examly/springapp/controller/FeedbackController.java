package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Feedback;
import com.examly.springapp.service.FeedbackService;
import com.examly.springapp.service.FeedbackServiceImpl;

/*
 * REST Controller for managing Feedback-related operations
 * Provides enpoints for creating, updating, retrieving, and deleting feedback entries
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /*
     * Constructor based Injection of FeedbackService
     */
    public FeedbackController(FeedbackServiceImpl feedbackService) {
        this.feedbackService = feedbackService;
    }

    
    /*
     * Enpoint to create a new Feedback entry.
     * returns HTTP 201 with created feedback, or 409 if creation fails
     */
    @PostMapping
    ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        Feedback savedfeedback = feedbackService.createFeedback(feedback);
        if(savedfeedback!=null){
            return ResponseEntity.status(201).body(savedfeedback);
        }else{
            return ResponseEntity.status(409).build();
        }
    }

    /*
     * Enpoint to retrieve a feedback by its ID
     * retuns HTTP 200 with feedback , or 404 if not found.
     */
    @GetMapping("/{feedbackId}")
    ResponseEntity<Feedback> getFeedbackById(@PathVariable long feedbackId){
        Feedback getFeedback = feedbackService.getFeedbackById(feedbackId).orElse(null);
        if(getFeedback!=null){
            return ResponseEntity.status(200).body(getFeedback);
        }else{
            return ResponseEntity.status(404).build();
        }
    }

    /*
     * Endpoint to retrieve all feedback entries.
     * return HTTP 200 with list of feedbacks, or 400 if retrieval fails
     */
    @GetMapping()
    ResponseEntity<List<Feedback>> getAllFeedbacks(){
        List<Feedback> feedbacks  = feedbackService.getAllFeedbacks();
        if(feedbacks!=null){
            return ResponseEntity.status(200).body(feedbacks);
        }else{
            return ResponseEntity.status(400).build();
        }
    }

    /*
     * Endpoint to retrieve feedbacks submitted by a specific user.
     * returns HTTP 200 with list of feedbacks, or 404 if none found.
     */
    @GetMapping("/user/{userId}")
    ResponseEntity<List<Feedback>> getFeedbacksByUserId(@PathVariable long userId){
        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
        if(feedbacks!=null){
            return ResponseEntity.status(200).body(feedbacks);
        }else{
            return ResponseEntity.status(404).build();
        }
    }

    /*
     * Enpoint to delete a feedback based on ID.
     * returns HTTP 200 if deleted, or 404 if not found.
     */
    @DeleteMapping("/{feedbackId}")
    ResponseEntity<String> deleteById(@PathVariable long feedbackId){
        Feedback feedback = feedbackService.getFeedbackById(feedbackId).orElse(null);
        if(feedback!=null){
            feedbackService.deleteFeedback(feedbackId);
            return ResponseEntity.status(200).body("Deleted Feedback successfully" + feedbackId);
        }else{
            return ResponseEntity.status(404).body("Cannot find a Feedback with this id" + feedbackId);
        }
    }
}
