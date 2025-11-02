package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.PropertyInquiry;

/*
 * Repository interface for PropertyInquiry entity.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface PropertyInquiryRepo extends JpaRepository<PropertyInquiry, Long> {

    /*
     * Custom query method to retrieve all PropertyInquiry records associated with a specific user ID.
     */
    List<PropertyInquiry> findAllByUserUserId(long userId);

    
    List<PropertyInquiry> findAllByPriority(String priority);

    List<PropertyInquiry> findAllByStatus(String status);

    boolean existsByProperty_PropertyIdAndUser_UserId(long propertyId, long userId);
}