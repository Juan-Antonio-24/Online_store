package com.example.OnlineStore.model;

import jakarta.persistence.*;
import java.io.Serializable;

import com.example.OnlineStore.DTO.ShippingMethodDTO;

@Entity
@Table(name = "shipping_methods")
public class ShippingMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idShippingMethod;

    private String shippingType;
    private double shippingCost;
    private String estimatedTime;
    private String shippingCompany;

    public ShippingMethod(ShippingMethodDTO dto) {
        this.idShippingMethod = dto.getIdShippingMethod();
        this.shippingType = dto.getShippingType();
        this.shippingCost = dto.getShippingCost();
        this.estimatedTime = dto.getEstimatedTime();
        this.shippingCompany = dto.getShippingCompany();
    }

    public ShippingMethod() {}

    public int getIdShippingMethod() {
        return idShippingMethod;
    }

    public void setIdShippingMethod(int idShippingMethod) {
        this.idShippingMethod = idShippingMethod;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }
}
