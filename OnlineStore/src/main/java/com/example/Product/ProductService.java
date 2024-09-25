package com.example.Product;

public interface ProductService {

    boolean addProduct(Product product);

    boolean deleteProduct(int productId);

    Product getProduct(int productId);

    Product[] getAllProducts();
}
