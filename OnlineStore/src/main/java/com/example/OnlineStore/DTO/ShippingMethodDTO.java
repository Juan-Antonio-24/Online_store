package com.example.OnlineStore.DTO;

import java.io.Serializable;

public class ShippingMethodDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idShippingMethod;
    private String shippingType;
    private double shippingCost;
    private String estimatedTime;
    private String shippingCompany;

    public ShippingMethodDTO() {}

    public ShippingMethodDTO(int idShippingMethod, String shippingType, double shippingCost, String estimatedTime, String shippingCompany) {
        this.idShippingMethod = idShippingMethod;
        this.shippingType = shippingType;
        this.shippingCost = shippingCost;
        this.estimatedTime = estimatedTime;
        this.shippingCompany = shippingCompany;
    }

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
