package backend.datn.services;


import backend.datn.dto.response.OrderDetailResponse;
import backend.datn.dto.response.OrderResponse;
import backend.datn.entities.*;
import backend.datn.exceptions.InsufficientStockException;
import backend.datn.exceptions.ResourceNotFoundException;
import backend.datn.mapper.OrderDetailMapper;
import backend.datn.mapper.OrderMapper;
import backend.datn.repositories.OrderDetailRepository;
import backend.datn.repositories.OrderRepository;
import backend.datn.repositories.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderSevice {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    public Page<OrderResponse> getAllOrder(String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> orderPage = orderRepository.searchOrder(search, pageable);

        return orderPage.map(OrderMapper::toOrderRespone);
    }

    public OrderResponse getOrderById(int id) {
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new RuntimeException("order not found with id: " + id));
        return OrderMapper.toOrderRespone(order);
    }

    @Transactional
    public OrderResponse toggleStatusOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voucher không co id: " + id));

        order.setStatusOrder(order.getStatusOrder() == 0 ? 1 : 0);
        order = orderRepository.save(order);
        return OrderMapper.toOrderRespone(order);
    }

    @Transactional
    public OrderResponse getOrderWithDetails(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hóa đơn với id: " + orderId));

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(OrderDetailMapper::toOrderDetailResponse)
                .collect(Collectors.toList());

        OrderResponse response = OrderMapper.toOrderRespone(order);
        response.setOrderDetailResponses(orderDetailResponses);

        return response;
    }

    private void validateVoucher(Voucher voucher, BigDecimal totalBill) {
        Instant now = Instant.now();

        if (totalBill.compareTo(voucher.getMinCondition()) < 0) {
            throw new RuntimeException("Đơn hàng chưa đạt giá trị tối thiểu để áp dụng Voucher");
        }
    }

    @Transactional
    public Order createOrder(Customer customer, Employee employee, Voucher voucher, List<OrderDetail> orderDetails, int paymentMethod) {
        Order order = new Order();

        order.setEmployee(employee);
        order.setVoucher(voucher);
        order.setCustomer(customer);
        order.setOrderCode("ORD" + System.currentTimeMillis());
        order.setCreateDate(Instant.now());
        order.setStatusOrder(0);
        order.setPaymentMethod(paymentMethod);

        int totalAmount = 0;
        BigDecimal totalBill = BigDecimal.ZERO;
        List<ProductDetail> updatedProducts = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            ProductDetail productDetail = productDetailRepository.findById(orderDetail.getProductDetail().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + orderDetail.getProductDetail().getId()));

            // Kiểm tra tồn kho
            if (productDetail.getQuantity() < orderDetail.getQuantity()) {
                throw new InsufficientStockException("Không đủ hàng trong kho: " + productDetail.getProductDetailCode());
            }

            // Trừ số lượng tồn kho
            productDetail.setQuantity(productDetail.getQuantity() - orderDetail.getQuantity());
            updatedProducts.add(productDetail);

            // Lấy khuyến mãi của sản phẩm (nếu có)
            Promotion promotion = productDetail.getPromotion();
            BigDecimal finalPrice = productDetail.getSalePrice();

            if (promotion != null) {
                BigDecimal discountPercent = BigDecimal.valueOf(100 - promotion.getPromotionPercent()).divide(BigDecimal.valueOf(100));
                finalPrice = finalPrice.multiply(discountPercent);
            }

            // Tính tổng giá trị đơn hàng trước khi áp dụng voucher
            totalBill = totalBill.add(finalPrice.multiply(BigDecimal.valueOf(orderDetail.getQuantity())));
            totalAmount += orderDetail.getQuantity();

            // Lưu chi tiết đơn hàng
            orderDetail.setOrder(order);
        }

        // Áp dụng giảm giá từ Voucher nếu có
        if (voucher != null) {
            validateVoucher(voucher, totalBill); // Kiểm tra tính hợp lệ

            // Tính giảm giá dựa trên % (Không vượt quá `maxDiscount`)
            BigDecimal discount = totalBill.multiply(BigDecimal.valueOf(voucher.getReducedPercent()).divide(BigDecimal.valueOf(100)));
            if (discount.compareTo(voucher.getMaxDiscount()) > 0) {
                discount = voucher.getMaxDiscount(); // Giới hạn mức giảm giá tối đa
            }

            totalBill = totalBill.subtract(discount);

            // Đảm bảo tổng tiền không âm
            if (totalBill.compareTo(BigDecimal.ZERO) < 0) {
                totalBill = BigDecimal.ZERO;
            }
        }

        order.setTotalBill(totalBill);
        order.setTotalAmount(totalAmount);

        // Lưu hóa đơn
        order = orderRepository.save(order);

        // Lưu toàn bộ chi tiết đơn hàng
        orderDetailRepository.saveAll(orderDetails);

        // Cập nhật số lượng tồn kho hàng loạt
        productDetailRepository.saveAll(updatedProducts);

        return order;
    }

    @Transactional
    public OrderResponse updateOrderStatusAfterPayment(Integer id) {
        // 1. Tìm đơn hàng theo ID
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + id));

        // 2. Kiểm tra xem đơn hàng đã được thanh toán chưa
        if (order.getStatusOrder() == 5) {
            throw new RuntimeException("Đơn hàng đã được thanh toán trước đó!");
        }

        // 3. Cập nhật trạng thái đơn hàng thành "Hoàn thành"
        order.setStatusOrder(5); // 1 = Đã hoàn thành

        // 4. Lưu thay đổi vào database
        order = orderRepository.save(order);

        // 5. Chuyển đổi và trả về kết quả
        return OrderMapper.toOrderRespone(order);
    }
}

