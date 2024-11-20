package com.example.OnlineStore.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

import com.example.OnlineStore.DTO.ShippingMethodDTO;
import com.example.OnlineStore.DTO.ShoppingCartDTO;

@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPayment;

    private String paymentMethod; 
    private String status; 

    @Temporal(TemporalType.TIMESTAMP) 
    private Date paymentDate;

    private double totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") 
    private Orders order; 

    public Payment() {}

    public Payment(String paymentMethod, Orders order, String status, double totalCost, Date paymentDate) {
        this.paymentMethod = paymentMethod;
        this.order = order;
        this.status = status; 
        this.totalCost = totalCost; 
        this.paymentDate = paymentDate; 
    }

    
    public int getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Orders getOrder() { 
        return order;
    }

    public void setOrder(Orders order) { 
        this.order = order;
    }

    
    public void processPayment(String paymentMethod, ShoppingCartDTO cart, ShippingMethodDTO shippingMethod, Orders order) {
        
        double shippingCost = shippingMethod.getShippingCost(); 
        double cartTotal = cart.getTotal(); 
        this.totalCost = cartTotal + shippingCost; 

        
        this.paymentMethod = paymentMethod;
        this.order = order;

        
        if (totalCost > 0) {
            this.status = "Processed"; 
            this.paymentDate = new Date(); 
        } else {
            this.status = "Failed"; 
        }
    }

    public String requestRefund() {
        return "Refund requested successfully"; 
    }
}



