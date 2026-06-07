package com.fnb.fnb_management.service;

import com.fnb.fnb_management.dto.OrderDetailRequest;
import com.fnb.fnb_management.dto.OrderRequest;
import com.fnb.fnb_management.entity.*;
import com.fnb.fnb_management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional // RẤT QUAN TRỌNG: Đảm bảo tính toàn vẹn dữ liệu
    public Order placeOrder(OrderRequest request) {
        // 1. Tạo hóa đơn trống
        Order order = new Order();
        order.setStatus("PAID");
        order.setTotalAmount(BigDecimal.ZERO);
        Order savedOrder = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 2. Duyệt qua từng món khách đặt
        for (OrderDetailRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn!"));

            // Cộng tiền
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            // Lưu chi tiết hóa đơn
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(savedOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPrice(product.getPrice());
            orderDetailRepository.save(orderDetail);

            // 3. LOGIC TRỪ KHO THEO CÔNG THỨC
            List<Recipe> recipes = recipeRepository.findByProductId(product.getId());
            for (Recipe recipe : recipes) {
                Ingredient ingredient = recipe.getIngredient();
                
                // Tính toán tổng nguyên liệu cần dùng = Định mức * Số lượng món
                BigDecimal totalRequired = recipe.getQuantityRequired().multiply(BigDecimal.valueOf(item.getQuantity()));
                
                // Kiểm tra xem kho còn đủ không
                if (ingredient.getStockQuantity().compareTo(totalRequired) < 0) {
                    throw new RuntimeException("Kho không đủ nguyên liệu: " + ingredient.getName());
                }
                
                // Trừ kho và lưu lại
                ingredient.setStockQuantity(ingredient.getStockQuantity().subtract(totalRequired));
                ingredientRepository.save(ingredient);
            }
        }

        // 4. Cập nhật lại tổng tiền cho hóa đơn
        savedOrder.setTotalAmount(totalAmount);
        return orderRepository.save(savedOrder);
    }
}