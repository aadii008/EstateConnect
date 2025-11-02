package com.examly.springapp.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class PropertyInquiry {
    @Id
    @GeneratedValue
    long inquiryId;
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    User user;
    @ManyToOne
    @JoinColumn(name = "propertyId",nullable = false)
    Property property;
    String message;
    String priority;
    String status;
    LocalDate inquiryDate;
    @Column(nullable = true)
    LocalDate responseDate;
    @Column(nullable = true)
    String adminResponse;
    String contactDetails;
    public PropertyInquiry() {
    }
    public PropertyInquiry(User user, Property property, String message, String priority, String status,
            LocalDate inquiryDate, LocalDate responseDate, String adminResponse, String contactDetails) {
        this.user = user;
        this.property = property;
        this.message = message;
        this.priority = priority;
        this.status = status;
        this.inquiryDate = inquiryDate;
        this.responseDate = responseDate;
        this.adminResponse = adminResponse;
        this.contactDetails = contactDetails;
    }
        // --- Getters ---
    public long getInquiryId() {
        return inquiryId;
    }

    public User getUser() {
        return user;
    }

    public Property getProperty() {
        return property;
    }

    public String getMessage() {
        return message;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getInquiryDate() {
        return inquiryDate;
    }

    public LocalDate getResponseDate() {
        return responseDate;
    }

    public String getAdminResponse() {
        return adminResponse;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    // --- Setters ---
    public void setInquiryId(long inquiryId) {
        this.inquiryId = inquiryId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInquiryDate(LocalDate inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public void setResponseDate(LocalDate responseDate) {
        this.responseDate = responseDate;
    }

    public void setAdminResponse(String adminResponse) {
        this.adminResponse = adminResponse;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

}