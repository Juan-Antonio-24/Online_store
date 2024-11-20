package com.example.OnlineStore.DTO;


public class OrdersDTO {
    private Integer idOrder;
    private String trackingNumber;
    private Integer shoppingCartId; 
    private Integer shippingMethodId; 
    private ShoppingCartDTO shoppingCart; 
    private ShippingMethodDTO shippingMethod; 

   
    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Integer getShippingMethodId() {
        return shippingMethodId;
    }

    public void setShippingMethodId(Integer shippingMethodId) {
        this.shippingMethodId = shippingMethodId;
    }

    public ShoppingCartDTO getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartDTO shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ShippingMethodDTO getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethodDTO shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
