package com.example.OnlineStore.DTO;

import java.io.Serializable;

public class PaymentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idPayment; 
    private String paymentMethod;  

    public PaymentDTO() {}

    public PaymentDTO(int idPayment, String paymentMethod) {
        this.idPayment = idPayment;
        this.paymentMethod = paymentMethod;
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
}
