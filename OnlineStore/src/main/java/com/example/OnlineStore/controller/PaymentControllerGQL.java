package com.example.OnlineStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.OnlineStore.model.Payment;
import com.example.OnlineStore.model.Orders;
import com.example.OnlineStore.service.PaymentService;
import com.example.OnlineStore.service.OrdersService;
import com.example.OnlineStore.DTO.PaymentDTO;
import com.example.OnlineStore.DTO.ProductsDTO;
import com.example.OnlineStore.DTO.ShippingMethodDTO;
import com.example.OnlineStore.DTO.ShoppingCartDTO;
import com.example.OnlineStore.DTO.UserDTO;
import com.example.OnlineStore.DTO.CategoryDTO;

@Controller
public class PaymentControllerGQL {

    private final PaymentService paymentService;
    private final OrdersService orderService;

    public PaymentControllerGQL(PaymentService paymentService, OrdersService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @QueryMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAll();
    }

    @QueryMapping
    public Payment getPaymentById(@Argument Integer paymentId) {
        return paymentService.getById(paymentId);
    }

    @MutationMapping
    public String processPayment(@Argument Integer orderId, @Argument PaymentDTO paymentDTO) {
        Orders order = orderService.getById(orderId);
        if (order == null) {
            return "Order not found";
        }

        List<ProductsDTO> productsDTOList = order.getCartProducts().stream()
            .map(product -> new ProductsDTO(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInInventory(),
                product.getDiscount(),
                product.getAvailableQuantity(),
                product.getWarehouseLocation(),
                new CategoryDTO()
            ))
            .collect(Collectors.toList());

        ShoppingCartDTO cart = new ShoppingCartDTO(
            order.getShoppingCart().getIdCart(),
            productsDTOList,
            order.getCartTotal(),
            new UserDTO(order.getShoppingCart().getUser())
        );

        ShippingMethodDTO shippingMethod = new ShippingMethodDTO(
            order.getShippingMethod().getIdShippingMethod(),
            order.getShippingMethod().getShippingType(),
            order.getShippingCost(),
            order.getShippingMethod().getEstimatedTime(),
            order.getShippingMethod().getShippingCompany()
        );

        paymentService.processPayment(orderId, paymentDTO, cart, shippingMethod);

        return "Payment processed successfully";
    }

    @MutationMapping
    public String updatePayment(@Argument PaymentDTO paymentDTO, @Argument Integer paymentId) {
        Payment auxPayment = paymentService.getById(paymentId);
        if (auxPayment != null) {
            paymentDTO.setIdPayment(auxPayment.getIdPayment());
            return "Payment updated successfully";
        } else {
            return "Payment not found";
        }
    }

    @MutationMapping
    public String deletePayment(@Argument Integer paymentId) {
        Payment payment = paymentService.getById(paymentId);
        if (payment != null) {
            paymentService.deletePayment(paymentId);
            return "Payment deleted successfully";
        } else {
            return "Payment not found";
        }
    }
}




