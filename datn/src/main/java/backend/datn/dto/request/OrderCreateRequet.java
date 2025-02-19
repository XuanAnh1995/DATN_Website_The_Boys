package backend.datn.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateRequet implements Serializable {

    @NotNull
    CustomerCreateRequest customer;
    @NotNull
    @Size(max = 50)
    String orderCode;
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