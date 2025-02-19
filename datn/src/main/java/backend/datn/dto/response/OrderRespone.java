package backend.datn.dto.response;


import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;


@Data
@Builder
public class OrderRespone implements Serializable {
    Integer id;
    EmployeeRespone employee;
    VoucherRespone voucher;
    CustomerRespone customer;
    String orderCode;
    Instant createDate;
    Integer totalAmount;
    BigDecimal totalBill;
    Integer paymentMethod;
    Integer statusOrder;
    Integer statusPayment;
    Integer kindOfOrder;
}