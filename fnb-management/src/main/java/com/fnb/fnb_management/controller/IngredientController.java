package com.fnb.fnb_management.controller;

import com.fnb.fnb_management.entity.Ingredient;
import com.fnb.fnb_management.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Cho phép React truy cập chéo nguồn
public class IngredientController {

    private final IngredientService ingredientService;

    // GET /api/ingredients -> Lấy danh sách kho
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAll() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    // PUT /api/ingredients/{id}/import?amount=50 -> Nhập thêm nguyên liệu
    @PutMapping("/{id}/import")
    public ResponseEntity<Ingredient> importStock(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(ingredientService.importStock(id, amount));
    }
}