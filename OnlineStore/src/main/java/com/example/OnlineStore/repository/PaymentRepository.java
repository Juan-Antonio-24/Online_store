package com.example.OnlineStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OnlineStore.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
