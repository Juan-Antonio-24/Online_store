package com.example.ShoppingCart;

import com.example.Product.Product; 
import com.example.User.User;       
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private int cartId;
    private List<Product> products = new ArrayList<>();
    private double total;
    private User user;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addProduct(Product product) {
        products.add(product);
        calculateTotal();
    }

    public void removeProduct(Product product) {
        products.remove(product);
        calculateTotal();
    }

    public void calculateTotal() {
        total = products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void emptyCart() {
        products.clear();
        total = 0.0;
    }

    public void applyDiscountCoupon(String coupon) {
        if (coupon.equals("DISCOUNT10")) {
            total *= 0.90;
        }
    }

    @Override
    public String toString() {
        return "ShoppingCart [cartId=" + cartId + ", products=" + products + ", total=" + total + ", user=" + user + "]";
    }
}

