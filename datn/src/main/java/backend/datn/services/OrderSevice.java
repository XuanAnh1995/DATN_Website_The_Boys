package backend.datn.services;


import backend.datn.dto.response.OrderDetailResponse;
import backend.datn.dto.response.OrderResponse;
import backend.datn.entities.Order;
import backend.datn.entities.OrderDetail;
import backend.datn.exceptions.ResourceNotFoundException;
import backend.datn.mapper.OrderDetailMapper;
import backend.datn.mapper.OrderMapper;
import backend.datn.repositories.OrderDetailRepository;
import backend.datn.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderSevice {
    @Autowired
    private OrderRepository orderRepository;
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
                .orElseThrow(() -> new ResourceNotFoundException("Voucher khong co id: " + id));

        order.setStatusOrder(order.getStatusOrder() ==0 ? 1: 0);
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

}

