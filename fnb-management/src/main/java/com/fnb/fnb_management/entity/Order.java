package com.fnb.fnb_management.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount; // Tổng tiền của hóa đơn

    @Column(nullable = false)
    private String status; // Trạng thái (VD: PAID, CANCELLED)

    @Column(name = "created_at")
    private LocalDateTime createdAt; // Thời gian tạo đơn

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}