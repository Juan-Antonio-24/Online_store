package com.example.OnlineStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.OnlineStore.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    

}
