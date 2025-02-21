package backend.datn.services;


import backend.datn.dto.response.OrderRespone;
import backend.datn.entities.Order;
import backend.datn.mapper.OrderMapper;
import backend.datn.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderSevice {
    @Autowired
    private OrderRepository orderRepository;

    public Page<OrderRespone> getAllOrder(String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> orderPage = orderRepository.searchOrder(search, pageable);

        return orderPage.map(OrderMapper::toOrderRespone);
    }

    public OrderRespone getOrderById(int id) {
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new RuntimeException("order not found with id: " + id));
        return OrderMapper.toOrderRespone(order);
    }



}

