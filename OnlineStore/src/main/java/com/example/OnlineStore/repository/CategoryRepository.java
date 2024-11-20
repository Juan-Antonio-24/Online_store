package com.example.OnlineStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OnlineStore.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}

