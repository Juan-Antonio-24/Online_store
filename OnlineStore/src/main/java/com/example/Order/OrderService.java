package com.example.Order;

public interface OrderService {

    boolean placeOrder(Order order);

    boolean cancelOrder(int orderId);

    Order getOrder(int orderId);

    Order[] getAllOrders();
}
