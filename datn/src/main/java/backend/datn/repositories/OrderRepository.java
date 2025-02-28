package backend.datn.repositories;


import backend.datn.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = """
        SELECT * FROM [order] 
        WHERE (:search IS NULL OR :search = '' OR LOWER(order_code) LIKE LOWER(CONCAT('%', :search, '%')))
    """, nativeQuery = true)
    Page<Order> searchOrder(@Param("search") String search,Pageable pageable);

    // Tìm đơn hàng theo mã
    Order findByOrderCode(String orderCode);

    // Tìm đơn hàng theo trạng thái
    List<Order> findByStatusOrder(int statusOrder);

    // Tìm đơn hàng theo khách hàng
    List<Order> findByCustomerId(int customerId);

}

