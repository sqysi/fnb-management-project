package com.fnb.fnb_management.repository;

import com.fnb.fnb_management.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // Hàm này rất quan trọng: Dùng để tìm xem 1 món ăn (Product) cần những nguyên liệu gì
    List<Recipe> findByProductId(Long productId);
}