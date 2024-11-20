package com.example.OnlineStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OnlineStore.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
}

