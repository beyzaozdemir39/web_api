package com.example.web_api.service;

import com.example.web_api.entities.Product;
import com.example.web_api.entities.Category;
import com.example.web_api.repos.ProductRepository;
import com.example.web_api.repos.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Transactional
    public Product createProduct(Product product, Long categoryId) {
        // Kategori kontrolü
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        product.setCategory(category); // Kategoriyi ürüne ata
        return productRepository.save(product);
    }
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct, Long categoryId) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    product.setStock(updatedProduct.getStock());
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
                    product.setCategory(category);

                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
