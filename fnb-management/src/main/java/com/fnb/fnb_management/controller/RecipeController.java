package com.fnb.fnb_management.controller;

import com.fnb.fnb_management.dto.RecipeRequest;
import com.fnb.fnb_management.entity.Recipe;
import com.fnb.fnb_management.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    // API: Xem món ăn này cần những nguyên liệu gì
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Recipe>> getRecipesByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(recipeService.getRecipesByProductId(productId));
    }

    // API: Thêm một nguyên liệu vào công thức món ăn
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeRequest request) {
        Recipe newRecipe = recipeService.addRecipe(
                request.getProductId(),
                request.getIngredientId(),
                request.getQuantityRequired()
        );
        return ResponseEntity.ok(newRecipe);
    }

    // API: Xóa nguyên liệu khỏi công thức
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok("Xóa nguyên liệu khỏi định mức thành công!");
    }
}