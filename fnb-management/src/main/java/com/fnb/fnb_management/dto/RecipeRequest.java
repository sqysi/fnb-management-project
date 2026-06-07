package com.fnb.fnb_management.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RecipeRequest {
    private Long productId;
    private Long ingredientId;
    private BigDecimal quantityRequired; // Số lượng cần dùng
}