package com.fnb.fnb_management.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name; // Tên nguyên liệu (Ví dụ: Sữa đặc Ông Thọ)

    @Column(nullable = false, length = 50)
    private String unit; // Đơn vị tính (Ví dụ: gram, ml, cái, hộp)

    @Column(nullable = false, name = "stock_quantity")
    private BigDecimal stockQuantity; // Số lượng tồn kho hiện tại
}