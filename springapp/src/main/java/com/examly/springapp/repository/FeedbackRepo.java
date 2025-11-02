package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Feedback;
/**
 * Repository interface for Feedback Entity 
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long>{

    /*
     * Retrieves a list of Feedback entries associated with a user ID
     */
    List<Feedback> findByUserUserId(long userId);

}
