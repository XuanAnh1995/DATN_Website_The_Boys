package backend.datn.controllers;

import backend.datn.dto.ApiResponse;
import backend.datn.dto.response.BrandResponse;
import backend.datn.dto.response.OrderRespone;
import backend.datn.entities.Order;
import backend.datn.mapper.OrderMapper;
import backend.datn.repositories.OrderRepository;


import backend.datn.services.OrderSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Đổi @Controller thành @RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderSevice orderService; // Đổi OrderSevice thành
    @Autowired
    OrderRepository orderRepository;

    @GetMapping
    public Page<OrderRespone> getAllOrder(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(page,size,sort);
        Page<Order> orderPage=orderRepository.searchOrder(search,pageable);
        return orderPage.map(OrderMapper::toOrderRespone);
    }
}

