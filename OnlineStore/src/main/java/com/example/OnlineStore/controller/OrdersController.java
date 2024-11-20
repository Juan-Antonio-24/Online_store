package com.example.OnlineStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors; 

@Tag(name = "6. Orders", description = "Controller for managing orders")
@RestController
@RequestMapping("orders")
@CrossOrigin(origins = "*")
public class OrdersController {

    @Autowired
    private OrdersService service;

    @Autowired
    private ShippingMethodService shippingMethodService; 

    @Autowired
    private ShoppingCartService shoppingCartService; 

    @Operation(summary = "Get all orders", responses = {
        @ApiResponse(responseCode = "200", description = "Orders found",
                     content = @Content(mediaType = "application/json",
                     array = @ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = OrdersDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No orders found")
    })
    @GetMapping
    public ResponseEntity<List<OrdersDTO>> getAll() {
        List<Orders> orders = service.getAll();
        List<OrdersDTO> ordersDTOs = orders.stream()
            .map(this::convertToDTO)
            .toList();
        return ResponseEntity.ok(ordersDTOs);
    }

    @Operation(summary = "Get order by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Order found",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = OrdersDTO.class))),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("{orderId}")
    public ResponseEntity<OrdersDTO> getById(@PathVariable Integer orderId) {
        Orders order = service.getById(orderId);
        return (order != null) ? ResponseEntity.ok(convertToDTO(order)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Place a new order", responses = {
        @ApiResponse(responseCode = "201", description = "Order placed successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = OrdersDTO.class)))
    })
    @PostMapping("{shoppingCartId}/{shippingMethodId}")
    public ResponseEntity<OrdersDTO> placeOrder(
            @PathVariable Integer shoppingCartId,
            @PathVariable Integer shippingMethodId) { 

        ShoppingCart shoppingCart = shoppingCartService.getById(shoppingCartId);
        ShippingMethod shippingMethod = shippingMethodService.findById(shippingMethodId);

        if (shoppingCart == null || shippingMethod == null) {
            return ResponseEntity.badRequest().body(null); 
        }

        Orders order = new Orders();
        order.setShoppingCart(shoppingCart);
        order.setShippingMethod(shippingMethod);
        
 

        service.save(order);

        OrdersDTO ordersDTO = convertToDTO(order); 
        return ResponseEntity.status(HttpStatus.CREATED).body(ordersDTO); 
    }

    @Operation(summary = "Update an existing order", responses = {
        @ApiResponse(responseCode = "200", description = "Order updated successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("{orderId}")
    public ResponseEntity<String> update(@RequestBody OrdersDTO ordersDTO, @PathVariable Integer orderId) {
        Orders auxOrder = service.getById(orderId);
        if (auxOrder != null) {
            auxOrder.setTrackingNumber(ordersDTO.getTrackingNumber());
            auxOrder.setShoppingCart(shoppingCartService.getById(ordersDTO.getShoppingCartId()));
            auxOrder.setShippingMethod(shippingMethodService.findById(ordersDTO.getShippingMethodId()));
            service.save(auxOrder);
            return ResponseEntity.ok("Order updated successfully");
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @Operation(summary = "Delete an order by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Order deleted successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("{orderId}")
    public ResponseEntity<String> delete(@PathVariable Integer orderId) {
        Orders auxOrder = service.getById(orderId);
        if (auxOrder != null) {
            service.delete(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } else {
            return ResponseEntity.notFound().build(); 
        }
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

            ordersDTO.setShoppingCartId(shoppingCart.getIdCart());
            ordersDTO.setShoppingCart(cartDTO);
        }

        ShippingMethod shippingMethod = order.getShippingMethod();
        if (shippingMethod != null) {
            ShippingMethodDTO methodDTO = new ShippingMethodDTO();
            methodDTO.setIdShippingMethod(shippingMethod.getIdShippingMethod());
            methodDTO.setShippingType(shippingMethod.getShippingType()); 
            
            ordersDTO.setShippingMethodId(shippingMethod.getIdShippingMethod());
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
