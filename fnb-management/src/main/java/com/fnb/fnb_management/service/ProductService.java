package com.fnb.fnb_management.service;

import com.fnb.fnb_management.entity.Product;
import com.fnb.fnb_management.entity.Category;
import com.fnb.fnb_management.repository.ProductRepository;
import com.fnb.fnb_management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // Lấy toàn bộ danh sách sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy chi tiết sản phẩm theo ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
    }

    // Lấy danh sách sản phẩm theo danh mục (Sử dụng hàm bạn đã viết trong Repository)
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Thêm mới hoặc Cập nhật sản phẩm
    public Product saveProduct(Product product) {
        // Kiểm tra xem danh mục truyền lên có tồn tại trong DB không
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new RuntimeException("Sản phẩm phải thuộc về một danh mục hợp lệ!");
        }
        
        Category category = categoryRepository.findById(product.getCategory().getId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + product.getCategory().getId()));
        
        // Gán ngược category đã tìm thấy vào product để đảm bảo tính toàn vẹn dữ liệu
        product.setCategory(category);
        
        return productRepository.save(product);
    }

    // Xóa sản phẩm
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}