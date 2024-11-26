package com.example.OnlineStore.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.OnlineStore.DTO.CategoryDTO;
import com.example.OnlineStore.model.Category;
import com.example.OnlineStore.service.CategoryService;

import java.util.List;

@Controller
public class CategoryControllerGQL {

    private final CategoryService categoryService;

    public CategoryControllerGQL(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @QueryMapping
    public Category getCategoryById(@Argument int id) {
        return categoryService.getCategoryById(id);
    }

    @MutationMapping
    public String addCategory(@Argument("category") CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO);
        return "Category added successfully";
    }

    @MutationMapping
    public String removeCategory(@Argument("id") int id) {
        categoryService.removeCategory(id);
        return "Category removed successfully";
    }

    @MutationMapping
    public String updateCategory(@Argument("id") int id, @Argument("category") CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return "Category updated successfully";
    }
}
