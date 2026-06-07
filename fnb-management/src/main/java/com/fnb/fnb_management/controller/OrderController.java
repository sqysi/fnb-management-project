package com.fnb.fnb_management.controller;

import com.fnb.fnb_management.dto.OrderRequest;
import com.fnb.fnb_management.entity.Order;
import com.fnb.fnb_management.service.InvoiceService;
import com.fnb.fnb_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final InvoiceService invoiceService;
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        try {
            Order newOrder = orderService.placeOrder(request);
            return ResponseEntity.ok(newOrder);
        } catch (RuntimeException e) {
            // Nếu kho hết nguyên liệu, trả về lỗi 400 kèm thông báo
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // API mới thêm vào: XUẤT HÓA ĐƠN PDF
    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> getInvoice(@PathVariable Long id) {
        byte[] pdfBytes = invoiceService.generateInvoicePdf(id);

        return ResponseEntity.ok()
                // Báo cho trình duyệt biết đây là file PDF
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + id + ".pdf")
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(pdfBytes);
    }
}