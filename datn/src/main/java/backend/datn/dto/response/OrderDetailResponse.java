package backend.datn.dto.response;

import backend.datn.entities.Order;
import backend.datn.entities.ProductDetail;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link backend.datn.entities.OrderDetail}
 */
@Data
@Builder
public class OrderDetailResponse implements Serializable {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}