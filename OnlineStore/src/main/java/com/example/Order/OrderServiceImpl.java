package com.example.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderServiceImpl implements OrderService {

    private static Map<Integer, Order> orders = new HashMap<>();

    @Override
    @GetMapping("{orderId}")
    public Order getOrder(@PathVariable("orderId") int orderId) {
        return orders.get(orderId);
    }

    @Override
    @PostMapping
    public boolean placeOrder(@RequestBody Order order) {
        if (orders.get(order.getOrderId()) != null)
            return false;
        orders.put(order.getOrderId(), order);
        return true;
    }

    @Override
    @DeleteMapping("{orderId}")
    public boolean cancelOrder(@PathVariable("orderId") int orderId) {
        if (orders.get(orderId) == null)
            return false;
        orders.remove(orderId);
        return true;
    }

    @Override
    @GetMapping
    public Order[] getAllOrders() {
        Set<Integer> ids = orders.keySet();
        Order[] orderArray = new Order[ids.size()];
        int i = 0;
        for (Integer id : ids) {
            orderArray[i] = orders.get(id);
            i++;
        }
        return orderArray;
    }
}
