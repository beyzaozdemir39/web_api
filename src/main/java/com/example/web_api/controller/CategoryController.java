package com.example.web_api.controller;

import com.example.web_api.entities.Category;
import com.example.web_api.exception.ResourceNotFoundException;
import com.example.web_api.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        logger.info("Fetching all categories");
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        logger.info("Fetching category with ID: {}", id);
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + id + " not found"));
        return ResponseEntity.ok(category);
    }
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        logger.info("Creating new category: {}", category);
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        logger.info("Updating category with ID: {}", id);
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Deleting category with ID: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
