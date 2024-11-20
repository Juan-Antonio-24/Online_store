package com.example.OnlineStore.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrder;

    @ManyToMany
    @JoinTable(
        name = "orders_products", 
        joinColumns = @JoinColumn(name = "order_id"), 
        inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Products> products;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @JsonManagedReference(value = "orderCart") 
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_method_id")
    @JsonManagedReference(value = "orderShippingMethod") 
    private ShippingMethod shippingMethod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id") 
    @JsonBackReference(value = "ordersPayment")
    private Payment payment; 

    private String trackingNumber;

    public Orders() {}

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    
    public List<Products> getCartProducts() {
        return shoppingCart != null ? shoppingCart.getProducts() : null;
    }

    
    public double getCartTotal() {
        return shoppingCart != null ? shoppingCart.getTotal() : 0.0;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public double getShippingCost() {
        return shippingMethod != null ? shippingMethod.getShippingCost() : 0.0;
    }

    public Payment getPayment() { 
        return payment;
    }

    public void setPayment(Payment payment) { 
        this.payment = payment;
    }

    public int getPaymentId() { 
        return payment != null ? payment.getIdPayment() : 0;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
