package com.example.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.Product.Product;
import com.example.User.User;
import com.example.payment.Payment;
import com.example.shippingMethod.ShippingMethod;
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private int orderId;
    private List<Product> products = new ArrayList<>();
    private Date date;
    private String status;
    private double total;
    private ShippingMethod shippingMethod;
    private Payment paymentDetails;
    private String trackingNumber;
    private User user;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Payment getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(Payment paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void placeOrder() {
        
    }

    public void viewOrderStatus() {
        
    }

    public void cancelOrder() {
        
    }

    public void generateInvoice() {
        
    }

    @Override
    public String toString() {
        return orderId + "::" + products + "::" + date + "::" + status + "::" + total + "::" + trackingNumber + "::" + user;
    }
}
