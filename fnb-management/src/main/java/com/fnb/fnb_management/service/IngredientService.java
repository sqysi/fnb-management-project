package com.fnb.fnb_management.service;

import com.fnb.fnb_management.entity.Ingredient;
import com.fnb.fnb_management.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    // Lấy toàn bộ danh sách nguyên liệu trong kho
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    // Cộng thêm số lượng khi nhập hàng vào kho
    @Transactional
    public Ingredient importStock(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Số lượng nhập kho phải lớn hơn 0!");
        }
        
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nguyên liệu với ID: " + id));
        
        // Cộng dồn vào lượng tồn kho hiện tại
        ingredient.setStockQuantity(ingredient.getStockQuantity().add(amount));
        
        return ingredientRepository.save(ingredient);
    }
}