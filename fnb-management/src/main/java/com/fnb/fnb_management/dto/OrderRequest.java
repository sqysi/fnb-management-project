package com.fnb.fnb_management.dto;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private List<OrderDetailRequest> items; // Danh sách các món khách gọi
}