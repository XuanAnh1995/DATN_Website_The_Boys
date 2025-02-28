// src/main/java/backend/datn/mapper/OrderDetailMapper.java
package backend.datn.mapper;

import backend.datn.dto.response.OrderDetailResponse;
import backend.datn.dto.response.ProductDetailResponse;
import backend.datn.entities.OrderDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailMapper {
    public static OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProductDetail().getProduct().getId())
                .productName(orderDetail.getProductDetail().getProduct().getProductName())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getProductDetail().getImportPrice() != null ? orderDetail.getProductDetail().getImportPrice() : BigDecimal.ZERO) // Đảm bảo không null
                .build();
    }
}
