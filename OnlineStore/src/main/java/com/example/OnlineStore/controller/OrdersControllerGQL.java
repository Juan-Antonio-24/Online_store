package com.example.OnlineStore.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.OnlineStore.DTO.OrdersDTO;
import com.example.OnlineStore.DTO.ProductsDTO;
import com.example.OnlineStore.DTO.ShippingMethodDTO;
import com.example.OnlineStore.DTO.ShoppingCartDTO;
import com.example.OnlineStore.model.Orders;
import com.example.OnlineStore.model.Products;
import com.example.OnlineStore.model.ShippingMethod;
import com.example.OnlineStore.model.ShoppingCart;
import com.example.OnlineStore.service.OrdersService;
import com.example.OnlineStore.service.ShippingMethodService;
import com.example.OnlineStore.service.ShoppingCartService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrdersControllerGQL {

    private final OrdersService service;
    private final ShippingMethodService shippingMethodService;
    private final ShoppingCartService shoppingCartService;

    public OrdersControllerGQL(OrdersService service, ShippingMethodService shippingMethodService, ShoppingCartService shoppingCartService) {
        this.service = service;
        this.shippingMethodService = shippingMethodService;
        this.shoppingCartService = shoppingCartService;
    }

    @QueryMapping
    public List<OrdersDTO> getAllOrders() {
        return service.getAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public OrdersDTO getOrderById(@Argument Integer orderId) {
        Orders order = service.getById(orderId);
        return (order != null) ? convertToDTO(order) : null;
    }

    @MutationMapping
    public OrdersDTO placeOrder(@Argument Integer shoppingCartId, @Argument Integer shippingMethodId) {
        ShoppingCart shoppingCart = shoppingCartService.getById(shoppingCartId);
        ShippingMethod shippingMethod = shippingMethodService.findById(shippingMethodId);

        if (shoppingCart == null || shippingMethod == null) {
            return null;
        }

        Orders order = new Orders();
        order.setShoppingCart(shoppingCart);
        order.setShippingMethod(shippingMethod);
        service.save(order);

        return convertToDTO(order);
    }

    private OrdersDTO convertToDTO(Orders order) {
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setIdOrder(order.getIdOrder());
        ordersDTO.setTrackingNumber(order.getTrackingNumber());

        ShoppingCart shoppingCart = order.getShoppingCart();
        if (shoppingCart != null) {
            ShoppingCartDTO cartDTO = new ShoppingCartDTO();
            cartDTO.setIdCart(shoppingCart.getIdCart());
            cartDTO.setTotal(shoppingCart.getTotal());

            if (shoppingCart.getProducts() != null) {
                List<ProductsDTO> productDTOs = shoppingCart.getProducts().stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
                cartDTO.setProducts(productDTOs);
            }

            ordersDTO.setShoppingCart(cartDTO);
        }

        ShippingMethod shippingMethod = order.getShippingMethod();
        if (shippingMethod != null) {
            ShippingMethodDTO methodDTO = new ShippingMethodDTO();
            methodDTO.setIdShippingMethod(shippingMethod.getIdShippingMethod());
            methodDTO.setShippingType(shippingMethod.getShippingType());

            ordersDTO.setShippingMethod(methodDTO);
        }

        return ordersDTO;
    }

    private ProductsDTO convertToDTO(Products product) {
        ProductsDTO productDTO = new ProductsDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
}


