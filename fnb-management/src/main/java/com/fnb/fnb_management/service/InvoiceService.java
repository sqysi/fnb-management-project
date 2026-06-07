package com.fnb.fnb_management.service;

import com.fnb.fnb_management.entity.Order;
import com.fnb.fnb_management.entity.OrderDetail;
import com.fnb.fnb_management.repository.OrderDetailRepository;
import com.fnb.fnb_management.repository.OrderRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public byte[] generateInvoicePdf(Long orderId) {
        // 1. Lấy thông tin đơn hàng từ DB
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));
        
        // Cần viết thêm 1 hàm findByOrderId trong OrderDetailRepository (Tôi sẽ hướng dẫn ở dưới)
        List<OrderDetail> details = orderDetailRepository.findByOrderId(orderId);

        // 2. Khởi tạo file PDF
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // 3. Vẽ Tiêu đề Quán
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("F&B COFFEE SHOP - INVOICE", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Dòng trống

            // Thông tin chung
            document.add(new Paragraph("Mã hóa đơn: #" + order.getId()));
            document.add(new Paragraph("Ngày giờ: " + order.getCreatedAt().toString()));
            document.add(new Paragraph("Trạng thái: " + order.getStatus()));
            document.add(new Paragraph(" "));

            // 4. Vẽ Bảng chi tiết món ăn
            PdfPTable table = new PdfPTable(3); // 3 Cột: Tên món, Số lượng, Giá
            table.setWidthPercentage(100);
            
            // Header bảng
            table.addCell(new PdfPCell(new Paragraph("Tên món")));
            table.addCell(new PdfPCell(new Paragraph("Số lượng")));
            table.addCell(new PdfPCell(new Paragraph("Thành tiền")));

            // Đổ dữ liệu món ăn vào bảng
            for (OrderDetail detail : details) {
                table.addCell(detail.getProduct().getName());
                table.addCell(String.valueOf(detail.getQuantity()));
                table.addCell(detail.getPrice().toString() + " VND");
            }
            document.add(table);
            document.add(new Paragraph(" "));

            // 5. Tổng tiền
            Font totalFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            Paragraph total = new Paragraph("TỔNG CỘNG: " + order.getTotalAmount() + " VND", totalFont);
            total.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(total);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo PDF: " + e.getMessage());
        } finally {
            document.close();
        }

        return out.toByteArray();
    }
}