package com.examly.springapp.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Feedback {

    @Id
    @GeneratedValue
    Long feedbackId;
    String feedbackText;
    LocalDate date;
    @ManyToOne
    @JoinColumn( name ="userId", nullable = false)
    User user;
    @ManyToOne
    @JoinColumn( name ="propertyId", nullable = true)
    Property property;
    String category;

    public Feedback() {
    }

    public Feedback(String feedbackText, LocalDate date, User user, Property property,String category) {
        this.feedbackText = feedbackText;
        this.date = date;
        this.user = user;
        this.property = property;
        this.category = category;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
