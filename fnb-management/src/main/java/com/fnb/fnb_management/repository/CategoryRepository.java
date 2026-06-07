package com.fnb.fnb_management.repository;

import com.fnb.fnb_management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Spring Data JPA đã cung cấp sẵn các hàm save(), findAll(), findById(), deleteById()...
}