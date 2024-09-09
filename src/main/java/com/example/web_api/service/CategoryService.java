// CategoryService.java
package com.example.web_api.service;

import com.example.web_api.dto.CategoryDTO;
import com.example.web_api.entities.Category;
import com.example.web_api.exception.DuplicateCategoryException;
import com.example.web_api.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if (existingCategory.isPresent()) {
            throw new DuplicateCategoryException("Category with the same name already exists.");
        }

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Optional<Category> categoryByName = categoryRepository.findByName(categoryDTO.getName());
        if (categoryByName.isPresent() && !categoryByName.get().getId().equals(id)) {
            throw new DuplicateCategoryException("Category with the same name already exists.");
        }

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(existingCategory);
    }
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
