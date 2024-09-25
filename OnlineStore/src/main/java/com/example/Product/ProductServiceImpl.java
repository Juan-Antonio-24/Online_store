package com.example.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductServiceImpl implements ProductService {

    private static Map<Integer, Product> products = new HashMap<>();

    @Override
    @GetMapping("{productId}")
    public Product getProduct(@PathVariable("productId") int productId) {
        return products.get(productId);
    }

    @Override
    @PostMapping
    public boolean addProduct(@RequestBody Product product) {
        if (products.get(product.getProductId()) != null)
            return false;
        products.put(product.getProductId(), product);
        return true;
    }

    @Override
    @DeleteMapping("{productId}")
    public boolean deleteProduct(@PathVariable("productId") int productId) {
        if (products.get(productId) == null)
            return false;
        products.remove(productId);
        return true;
    }

    @Override
    @GetMapping
    public Product[] getAllProducts() {
        Set<Integer> ids = products.keySet();
        Product[] productArray = new Product[ids.size()];
        int i = 0;
        for (Integer id : ids) {
            productArray[i] = products.get(id);
            i++;
        }
        return productArray;
    }
}
