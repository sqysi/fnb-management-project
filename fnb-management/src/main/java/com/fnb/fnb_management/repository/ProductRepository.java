package com.fnb.fnb_management.repository;

import com.fnb.fnb_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Viết thêm hàm tìm kiếm sản phẩm theo danh mục
    List<Product> findByCategoryId(Long categoryId);
}