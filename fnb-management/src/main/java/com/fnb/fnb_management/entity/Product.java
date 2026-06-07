package com.fnb.fnb_management.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name; // Tên món ăn/đồ uống

    @Column(nullable = false)
    private BigDecimal price; // Giá bán

    private String imageUrl; // Link ảnh sản phẩm

    @Column(name = "is_active")
    private Boolean active = true; // Trạng thái kinh doanh (true: đang bán, false: ngừng bán)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Khóa ngoại liên kết tới bảng categories
}