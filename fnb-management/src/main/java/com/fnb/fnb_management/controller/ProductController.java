package com.fnb.fnb_management.controller;

import com.fnb.fnb_management.entity.Product;
import com.fnb.fnb_management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 1. API Lấy toàn bộ sản phẩm (GET /api/products)
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 2. API Lấy 1 sản phẩm theo ID (GET /api/products/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // 3. API Lấy sản phẩm theo danh mục (GET /api/products/category/{categoryId})
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    // 4. API Tạo mới sản phẩm (POST /api/products)
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // 5. API Cập nhật sản phẩm (PUT /api/products/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productService.getProductById(id);
        
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setImageUrl(productDetails.getImageUrl());
        product.setActive(productDetails.getActive());
        product.setCategory(productDetails.getCategory());
        
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // 6. API Xóa sản phẩm (DELETE /api/products/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Xóa sản phẩm thành công!");
    }
}