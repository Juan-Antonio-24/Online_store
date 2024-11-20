package com.example.OnlineStore.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OnlineStore.model.Orders;
import com.example.OnlineStore.model.ShoppingCart;
import com.example.OnlineStore.model.ShippingMethod;
import com.example.OnlineStore.repository.OrdersRepository;
import com.example.OnlineStore.exception.ResourceNotFoundException;
import com.example.OnlineStore.exception.OrderNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdersServiceImpl {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShippingMethodService shippingMethodService;

    public List<Orders> getAll() {
        return ordersRepository.findAll();
    }

    public Orders getById(Integer orderId) {
        
        return ordersRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order", String.valueOf(orderId)));
    }

    public String placeOrder(Integer shoppingCartId, Integer shippingMethodId) {
        ShoppingCart shoppingCart = shoppingCartService.getById(shoppingCartId);
        ShippingMethod shippingMethod = shippingMethodService.findById(shippingMethodId);

        
        if (shoppingCart == null) {
            throw new ResourceNotFoundException("ShoppingCart", String.valueOf(shoppingCartId));
        }
        if (shippingMethod == null) {
            throw new ResourceNotFoundException("ShippingMethod", String.valueOf(shippingMethodId));
        }

        Orders order = new Orders();
        order.setShoppingCart(shoppingCart);
        order.setShippingMethod(shippingMethod);

        if (order.getTrackingNumber() == null || order.getTrackingNumber().isEmpty()) {
            order.setTrackingNumber(generateTrackingNumber());
        }

        ordersRepository.save(order);
        return "Order placed successfully";
    }

    public String update(Orders order) {
        
        if (!ordersRepository.existsById(order.getIdOrder())) {
            throw new ResourceNotFoundException("Orders", String.valueOf(order.getIdOrder()));
        }
        ordersRepository.save(order);
        return "Order updated successfully";
    }

    public String delete(Integer orderId) {
        
        if (!ordersRepository.existsById(orderId)) {
            throw new OrderNotFoundException(String.valueOf(orderId));
        }
        ordersRepository.deleteById(orderId);
        return "Order deleted successfully";
    }

    private String generateTrackingNumber() {
        return "TRACK-" + System.currentTimeMillis();
    }
}




