package backend.datn.dto.request;

import backend.datn.entities.Customer;
import backend.datn.entities.Employee;
import backend.datn.entities.OrderDetail;
import backend.datn.entities.Voucher;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PosOrderRequest {

    private Customer customer;

    private Employee employee;

    private Voucher voucher;

    private List<OrderDetail> orderDetails;

    private int paymentMethod; // 1: Tiền mặt, 2: Chuyển khoản, 3: QR Code

}

