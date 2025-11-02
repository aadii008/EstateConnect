package com.examly.springapp.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Property;

@Repository
public interface PropertyRepo extends JpaRepository<Property,Long>{
    Optional<Property> findByTitle(String title);
}
