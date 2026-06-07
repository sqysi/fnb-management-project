package com.fnb.fnb_management.dto;
import lombok.Data;

@Data
public class OrderDetailRequest {
    private Long productId;
    private Integer quantity;
}