package backend.datn.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdateRequest implements Serializable {

    EmployeeCreateRequest employeeRequet;
    VoucherCreateRequest voucherRequet;
    @NotNull
    CustomerCreateRequest customerRequet;

    @NotNull
    Instant createDate;
    @NotNull
    Integer totalAmount;
    @NotNull
    BigDecimal totalBill;
    @NotNull
    Integer paymentMethod;
    @NotNull
    Integer statusOrder;
    @NotNull
    Integer statusPayment;
    @NotNull
    Integer kindOfOrder;
}