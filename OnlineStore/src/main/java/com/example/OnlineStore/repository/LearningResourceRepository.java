package com.example.OnlineStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.OnlineStore.model.LearningResource;

@Repository
public interface LearningResourceRepository extends JpaRepository<LearningResource, Integer> {
    
}

