package backend.datn.controllers;

import backend.datn.dto.ApiResponse;
import backend.datn.dto.response.BrandResponse;
import backend.datn.dto.response.OrderResponse;
import backend.datn.exceptions.EntityNotFoundException;
import backend.datn.repositories.OrderRepository;
import backend.datn.services.OrderSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderSevice orderService;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<ApiResponse> getAlOrder(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<OrderResponse> orderPage = orderService.getAllOrder(search, page, size, sortBy, sortDir);
            ApiResponse response = new ApiResponse("success", "Get all order successfully", orderPage);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the order list", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOrderId(@PathVariable int id) {
        try {
            OrderResponse orderRespone = orderService.getOrderById(id);
            ApiResponse response = new ApiResponse("success", "Get brand by id successfully", orderRespone);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "An error occurred while retrieving the order", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse> toggleStatusOrder(@PathVariable Integer id){
        try {
            OrderResponse orderResponse = orderService.toggleStatusOrder(id);
            ApiResponse response = new ApiResponse("success", "Chuyển đổi trạng thái hóa đơn thành công", orderResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Đã xảy ra lỗi khi chuyển đổi trạng thái của hóa đơn", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{id}/details")
    public ResponseEntity<ApiResponse> getOrderDetails(@PathVariable int id) {
        try {
            OrderResponse orderResponse = orderService.getOrderWithDetails(id);
            ApiResponse response = new ApiResponse("success", "Lấy chi tiết hóa đơn thành công", orderResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("error", "Đã xảy ra lỗi khi lấy chi tiết hóa đơn", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

