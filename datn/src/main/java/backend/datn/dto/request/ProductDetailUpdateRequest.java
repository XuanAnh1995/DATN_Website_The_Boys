package backend.datn.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDetailUpdateRequest {
    @NotNull(message = "Sản phẩm không được để trống")
    private Integer productId;

    @NotNull(message = "Kích thước không được để trống")
    private Integer sizeId;

    @NotNull(message = "Màu sắc không được để trống")
    private Integer colorId;

    @NotNull(message = "Khuyến mãi không được để trống")
    private Integer promotionId;

    @NotEmpty(message = "Cổ áo không được để trống")
    private Integer collarId;

    @NotEmpty(message = "Tay áo không được để trống")
    private Integer sleeveId;

    @Size(max = 250, message = "Ảnh không được vượt quá 250 ký tự")
    @NotNull(message = "Ảnh không được để trống")
    private String photo;

    @Size(max = 50, message = "Mã chi tiết sản phẩm không được vượt quá 50 ký tự")
    @NotNull(message = "Mã chi tiết sản phẩm không được để trống")
    private String productDetailCode;

    @NotNull(message = "Giá nhập không được để trống")
    private BigDecimal importPrice;

    @NotNull(message = "Giá bán không được để trống")
    private BigDecimal salePrice;

    @NotNull(message = "Số lượng không được để trống")
    private Integer quantity;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    @NotNull(message = "Mô tả không được để trống")
    private String description;

}