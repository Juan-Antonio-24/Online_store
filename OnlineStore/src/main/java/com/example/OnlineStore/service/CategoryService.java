package com.example.OnlineStore.service;

import java.util.List;
import com.example.OnlineStore.DTO.CategoryDTO;
import com.example.OnlineStore.model.Category;

public interface CategoryService {
    List<Category> getAllCategories(); 
    Category getCategoryById(int id);  
    Category addCategory(CategoryDTO categoryDTO); 
    void removeCategory(int id); 
    Category updateCategory(int id, CategoryDTO categoryDTO);
}
