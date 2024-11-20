package com.example.OnlineStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OnlineStore.model.Orders;
import com.example.OnlineStore.repository.OrdersRepository;
import com.example.OnlineStore.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdersService {
    @Autowired
    private OrdersRepository repo;

    public List<Orders> getAll() {
        return repo.findAll();
    }

    public void save(Orders order) {
        
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null"); 
        }

        if (order.getTrackingNumber() == null || order.getTrackingNumber().isEmpty()) {
            order.setTrackingNumber(generateTrackingNumber());
        }
        repo.save(order);
    }

    public Orders getById(Integer orderId) {
        
        return repo.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order", orderId.toString()));
    }

    public void delete(Integer orderId) {
        getById(orderId);
        repo.deleteById(orderId);
    }
    

    public String generateTrackingNumber() {
        return "TRACK-" + System.currentTimeMillis();
    }
}



