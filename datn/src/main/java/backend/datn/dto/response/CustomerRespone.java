package backend.datn.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;


@Data
@Builder
public class CustomerRespone implements Serializable {
    Integer id;
    String customerCode;
    String fullname;
    String username;
    String password;
    String email;
    String phone;
    String address;
    Instant createDate;
    Instant updateDate;
    Boolean forgetPassword;
    Integer status;
}