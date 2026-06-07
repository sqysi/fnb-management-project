package com.fnb.fnb_management.service;

import com.fnb.fnb_management.entity.Ingredient;
import com.fnb.fnb_management.entity.Product;
import com.fnb.fnb_management.entity.Recipe;
import com.fnb.fnb_management.repository.IngredientRepository;
import com.fnb.fnb_management.repository.ProductRepository;
import com.fnb.fnb_management.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    private final IngredientRepository ingredientRepository;

    // 1. Lấy toàn bộ công thức của một món ăn cụ thể
    public List<Recipe> getRecipesByProductId(Long productId) {
        return recipeRepository.findByProductId(productId);
    }

    // 2. Thêm nguyên liệu vào định mức của món ăn
    public Recipe addRecipe(Long productId, Long ingredientId, BigDecimal quantityRequired) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy Sản phẩm với ID: " + productId));
            
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy Nguyên liệu với ID: " + ingredientId));

        Recipe recipe = new Recipe();
        recipe.setProduct(product);
        recipe.setIngredient(ingredient);
        recipe.setQuantityRequired(quantityRequired);

        return recipeRepository.save(recipe);
    }

    // 3. Xóa một nguyên liệu khỏi công thức
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }
}