package com.example.OnlineStore.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "4. ShoppingCarts", description = "Controller for managing shopping carts")
@RestController
@RequestMapping("shopping-carts")
@CrossOrigin(origins = "*")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;

    @Autowired
    private ProductsService productService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all shopping carts", responses = {
        @ApiResponse(responseCode = "200", description = "Shopping carts found",
                     content = @Content(mediaType = "application/json",
                     array = @ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ShoppingCartDTO.class)))),
        @ApiResponse(responseCode = "404", description = "No shopping carts found")
    })
    @GetMapping
    public List<ShoppingCartDTO> getAll() {
        List<ShoppingCart> carts = service.getAll();
        return convertToDTOs(carts);
    }

    @Operation(summary = "Get shopping cart by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Shopping cart found",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ShoppingCartDTO.class))),
        @ApiResponse(responseCode = "404", description = "Shopping cart not found")
    })
    @GetMapping("{cartId}")
    public ResponseEntity<?> getById(@PathVariable Integer cartId) {
        ShoppingCart cart = service.getById(cartId);
        if (cart != null) {
            return new ResponseEntity<>(convertToDTO(cart), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Shopping cart not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a product to the shopping cart", responses = {
        @ApiResponse(responseCode = "200", description = "Product added to shopping cart",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Shopping cart or product not found"),
        @ApiResponse(responseCode = "409", description = "Product already exists in shopping cart")
    })
    @PostMapping("{cartId}/{userId}/add-product/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable Integer cartId, @PathVariable Integer userId, @PathVariable Integer productId) {
        Products product = productService.getById(productId);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        ShoppingCart cart = service.getById(cartId);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setProducts(new ArrayList<>());
            User user = userService.getById(userId);
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            cart.setUser(user);
            service.save(cart);
        }

        if (cart.getProducts().contains(product)) {
            return new ResponseEntity<>("Product already exists in shopping cart", HttpStatus.CONFLICT);
        }

        cart.addProduct(product);
        service.save(cart);
        return new ResponseEntity<>("Product added to shopping cart", HttpStatus.OK);
    }

    @Operation(summary = "Delete a product from the shopping cart", responses = {
        @ApiResponse(responseCode = "200", description = "Product deleted from shopping cart",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Shopping cart or product not found")
    })
    @DeleteMapping("{cartId}/remove-product/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Integer cartId, @PathVariable Integer productId) {
        ShoppingCart cart = service.getById(cartId);
        Products product = productService.getById(productId);

        if (cart == null) {
            return new ResponseEntity<>("Shopping cart not found", HttpStatus.NOT_FOUND);
        }

        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        if (cart.getProducts().remove(product)) {
            cart.calculateTotal();
            service.save(cart);

            if (cart.getProducts().isEmpty()) {
                service.delete(cart.getIdCart());
                return new ResponseEntity<>("Product deleted from shopping cart and cart is empty", HttpStatus.OK);
            }
            return new ResponseEntity<>("Product deleted from shopping cart", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found in shopping cart", HttpStatus.NOT_FOUND);
        }
    }

    private ShoppingCartDTO convertToDTO(ShoppingCart cart) {
        List<ProductsDTO> productsDTO = new ArrayList<>();
        for (Products product : cart.getProducts()) {
            CategoryDTO categoryDTO = null;
            if (product.getCategory() != null) {
                categoryDTO = new CategoryDTO(product.getCategory().getIdCategory(),
                                               product.getCategory().getName(),
                                               product.getCategory().getDescription());
            }
            productsDTO.add(new ProductsDTO(product.getProductId(), product.getName(), product.getDescription(),
                                              product.getPrice(), product.getQuantityInInventory(),
                                              product.getDiscount(), product.getAvailableQuantity(),
                                              product.getWarehouseLocation(), categoryDTO));
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
                cart.getUser().getPhone(),
                cart.getUser().getPassword() 
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

