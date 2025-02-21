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
    Integer paymentMethod;
    @NotNull
    Integer statusOrder;
    @NotNull
    Integer kindOfOrder;
}