package com.example.web_api.controller;

import com.example.web_api.dto.ProductDTO;
import com.example.web_api.entities.Product;
import com.example.web_api.exception.ResourceNotFoundException;
import com.example.web_api.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));
        return ResponseEntity.ok(product);
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO.getCategoryId() == null) {
            throw new RuntimeException("Category ID cannot be null");
        }
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        Product savedProduct = productService.createProduct(product, productDTO.getCategoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        if (productDTO.getCategoryId() == null) {
            throw new RuntimeException("Category ID cannot be null");
        }
        Product updatedProduct = new Product();
        updatedProduct.setName(productDTO.getName());
        updatedProduct.setPrice(productDTO.getPrice());
        updatedProduct.setStock(productDTO.getStock());
        Product savedProduct = productService.updateProduct(id, updatedProduct, productDTO.getCategoryId());
        return ResponseEntity.ok(savedProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        logger.info("Fetching products for category ID: {}", categoryId);
        List<Product> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}
