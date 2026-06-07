package com.fnb.fnb_management.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Thuộc món ăn nào

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient; // Cần nguyên liệu nào

    @Column(nullable = false, name = "quantity_required")
    private BigDecimal quantityRequired; // Số lượng nguyên liệu cần tiêu hao cho 1 đơn vị sản phẩm
}