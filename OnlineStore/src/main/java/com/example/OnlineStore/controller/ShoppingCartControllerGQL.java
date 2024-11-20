package com.example.OnlineStore.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.OnlineStore.DTO.CategoryDTO;
import com.example.OnlineStore.DTO.ProductsDTO;
import com.example.OnlineStore.DTO.ShoppingCartDTO;
import com.example.OnlineStore.DTO.UserDTO;
import com.example.OnlineStore.model.Products;
import com.example.OnlineStore.model.ShoppingCart;
import com.example.OnlineStore.model.User;
import com.example.OnlineStore.service.ProductsService;
import com.example.OnlineStore.service.ShoppingCartService;
import com.example.OnlineStore.service.UserService;

@Controller
public class ShoppingCartControllerGQL {

    private final ShoppingCartService service;
    private final ProductsService productService;
    private final UserService userService;


    public ShoppingCartControllerGQL(ShoppingCartService service, ProductsService productService, UserService userService) {
        this.service = service;
        this.productService = productService;
        this.userService = userService;
    }

    @QueryMapping
    public List<ShoppingCartDTO> getAllShoppingCarts() {
        List<ShoppingCart> carts = service.getAll();
        return convertToDTOs(carts);
    }

    @QueryMapping
    public ShoppingCartDTO getShoppingCartById(@Argument Integer cartId) {
        ShoppingCart cart = service.getById(cartId);
        return cart != null ? convertToDTO(cart) : null;
    }

    @MutationMapping
    public String addProductToShoppingCart(@Argument Integer cartId, @Argument Integer userId, @Argument Integer productId) {
        Products product = productService.getById(productId);
        if (product == null) {
            return "Product not found";
        }

        ShoppingCart cart = service.getById(cartId);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setProducts(new ArrayList<>());
            User user = userService.getById(userId);
            if (user == null) {
                return "User not found";
            }
            cart.setUser(user);
            service.save(cart);
        }

        if (cart.getProducts().contains(product)) {
            return "Product already exists in shopping cart";
        }

        cart.addProduct(product);
        service.save(cart);
        return "Product added to shopping cart";
    }

    @MutationMapping
    public String removeProductFromShoppingCart(@Argument Integer cartId, @Argument Integer productId) {
        ShoppingCart cart = service.getById(cartId);
        Products product = productService.getById(productId);

        if (cart == null) {
            return "Shopping cart not found";
        }

        if (product == null) {
            return "Product not found";
        }

        if (cart.getProducts().remove(product)) {
            cart.calculateTotal();
            service.save(cart);

            if (cart.getProducts().isEmpty()) {
                service.delete(cart.getIdCart());
                return "Product deleted from shopping cart and cart is empty";
            }
            return "Product deleted from shopping cart";
        } else {
            return "Product not found in shopping cart";
        }
    }

    private ShoppingCartDTO convertToDTO(ShoppingCart cart) {
        List<ProductsDTO> productsDTO = new ArrayList<>();
        for (Products product : cart.getProducts()) {
            CategoryDTO categoryDTO = null;
            if (product.getCategory() != null) {
                categoryDTO = new CategoryDTO(
                    product.getCategory().getIdCategory(),
                    product.getCategory().getName(),
                    product.getCategory().getDescription()
                );
            }
            productsDTO.add(new ProductsDTO(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInInventory(),
                product.getDiscount(),
                product.getAvailableQuantity(),
                product.getWarehouseLocation(),
                categoryDTO
            ));
        }
        return new ShoppingCartDTO(
            cart.getIdCart(),
            productsDTO,
            cart.getTotal(),
            new UserDTO(
                cart.getUser().getUserId(),
                cart.getUser().getName(),
                cart.getUser().getEmail(),
                cart.getUser().getAddress(),
                cart.getUser().getUserType(),
                cart.getUser().getPhone()
            )
        );
    }

    private List<ShoppingCartDTO> convertToDTOs(List<ShoppingCart> carts) {
        List<ShoppingCartDTO> dtos = new ArrayList<>();
        for (ShoppingCart cart : carts) {
            dtos.add(convertToDTO(cart));
        }
        return dtos;
    }
}


