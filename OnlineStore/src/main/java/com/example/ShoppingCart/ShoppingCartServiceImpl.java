package com.example.ShoppingCart;

import com.example.Product.Product;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("carts")
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static Map<Integer, ShoppingCart> carts = new HashMap<>();

    @Override
    @GetMapping("{cartId}")
    public ShoppingCart getCart(@PathVariable("cartId") int cartId) {
        return carts.get(cartId);
    }

    @Override
    @PostMapping
    public boolean addProduct(@RequestParam int cartId, @RequestBody Product product) {
        ShoppingCart cart = carts.get(cartId);
        if (cart == null) {
            return false;
        }
        cart.addProduct(product);
        return true;
    }

    @Override
    @DeleteMapping("{cartId}")
    public boolean removeProduct(@PathVariable("cartId") int cartId, @RequestBody Product product) {
        ShoppingCart cart = carts.get(cartId);
        if (cart == null) {
            return false;
        }
        cart.removeProduct(product);
        return true;
    }

    @Override
    @GetMapping
    public ShoppingCart[] getAllCarts() {
        Set<Integer> ids = carts.keySet();
        ShoppingCart[] cartArray = new ShoppingCart[ids.size()];
        int i = 0;
        for (Integer id : ids) {
            cartArray[i] = carts.get(id);
            i++;
        }
        return cartArray;
    }
}
