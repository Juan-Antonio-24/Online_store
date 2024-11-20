package com.example.OnlineStore.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OnlineStore.model.Payment;
import com.example.OnlineStore.model.Orders;
import com.example.OnlineStore.repository.PaymentRepository;
import com.example.OnlineStore.repository.OrdersRepository;
import com.example.OnlineStore.DTO.PaymentDTO;
import com.example.OnlineStore.DTO.ShoppingCartDTO;
import com.example.OnlineStore.DTO.ShippingMethodDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Payment getById(Integer paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    public Payment processPayment(Integer orderId, PaymentDTO paymentDTO, ShoppingCartDTO cart, ShippingMethodDTO shippingMethod) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        
        Payment payment = new Payment();
        
        
        double shippingCost = shippingMethod.getShippingCost(); 
        double cartTotal = cart.getTotal(); 
        double totalCost = cartTotal + shippingCost; 

        
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setTotalCost(totalCost);
        payment.setStatus("Completed");
        payment.setPaymentDate(new Date());
        payment.setOrder(order);

        
        paymentRepository.save(payment);
        ordersRepository.save(order); 

        return payment; 
    }

    public void deletePayment(Integer paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}

