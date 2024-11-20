package com.example.OnlineStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.OnlineStore.DTO.CategoryDTO; 
import com.example.OnlineStore.model.Category;
import com.example.OnlineStore.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "1. Category", description = "Controller for managing categories")
@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*") 
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get all categories", responses = {
        @ApiResponse(responseCode = "200", description = "Categories found",
                     content = @Content(mediaType = "application/json",
                     array = @ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Category.class)))),
        @ApiResponse(responseCode = "404", description = "Categories not found")
    })
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories); 
    }

    @Operation(summary = "Get category by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Category found",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Category.class))),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(category); 
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }

    @Operation(summary = "Add a new category", responses = {
        @ApiResponse(responseCode = "201", description = "Category created",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Category.class)))
    })
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody CategoryDTO categoryDTO) { 
        Category createdCategory = categoryService.addCategory(categoryDTO); 
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory); 
    }

    @Operation(summary = "Remove a category by ID", responses = {
        @ApiResponse(responseCode = "204", description = "Category removed"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCategory(@PathVariable int id) {
        categoryService.removeCategory(id);
        return ResponseEntity.noContent().build(); 
    }

    @Operation(summary = "Update a category by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Category updated",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Category.class))),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) { 
        Category updatedCategory = categoryService.updateCategory(id, categoryDTO); 
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory); 
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }
}



