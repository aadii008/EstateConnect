package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.exceptions.DuplicatePropertyException;
import com.examly.springapp.exceptions.PropertyException;
import com.examly.springapp.model.Property;
import com.examly.springapp.repository.PropertyRepo;


// Marks this class as a service component in the Spring context
@Service
public class PropertyServiceImpl implements PropertyService{
    
    
    // Injects the Property repository for database operations
    private final PropertyRepo propertyRepo;

    
    // Constructor-based dependency injection
    public PropertyServiceImpl(PropertyRepo propertyRepo) {
        this.propertyRepo = propertyRepo;
    }

    
    /**
     * Adds a new property to the database.
     * @param property Property object to be saved
     * @return Saved Property object
     */
    @Override
    public Property addProperty(Property property) {
        Optional<Property> existingProperty = propertyRepo.findByTitle(property.getTitle());
        if (existingProperty.isPresent()) {
            throw new DuplicatePropertyException("Property with title '" + property.getTitle() + "' already exists.");
        }
        return propertyRepo.save(property);
    }
 
    /**
     * Retrieves a property by its ID.
     * @param propertyId ID of the property
     * @return Optional containing the Property if found, else empty
     */
    @Override
    public Optional<Property> getPropertyById(long propertyId){
        return propertyRepo.findById(propertyId);
    }


    /**
     * Retrieves all properties from the database.
     * @return List of all Property objects
     */
    @Override
    public List<Property> getAllProperties(){
        return propertyRepo.findAll();
    }
 
    /**
     * Updates an existing property with new details.
     * @param propertyId ID of the property to update
     * @param property Property object containing updated data
     * @return Updated Property object
     * @throws PropertyException if the property is not found
     */
    @Override
    public Property updateProperty(long propertyId, Property property) {
        Optional<Property> updatedProperty = propertyRepo.findById(propertyId);
        if (updatedProperty.isPresent()) {
            Property updateProperty = updatedProperty.get();
            updateProperty.setPropertyId(propertyId);
            updateProperty.setTitle(property.getTitle());
            updateProperty.setDescription(property.getDescription());
            updateProperty.setLocation(property.getLocation());
            updateProperty.setPrice(property.getPrice());
            updateProperty.setType(property.getType());
            updateProperty.setStatus(property.getStatus());
            return propertyRepo.save(updateProperty);
        } 

        else {
            throw new PropertyException("Property not found with ID: " + propertyId);
        }
    }

    /**
     * Deletes a property by its ID.
     * @param propertyId ID of the property to delete
     */
    @Override
    public void deleteProperty(long propertyId){
        Optional<Property> property=propertyRepo.findById(propertyId);
        if(property.isPresent()){
            propertyRepo.deleteById(propertyId);
        }
    }

}
