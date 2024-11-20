package com.example.OnlineStore.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("Order with ID '" + orderId + "' was not found. Please verify the order ID and try again.");
    }
}
