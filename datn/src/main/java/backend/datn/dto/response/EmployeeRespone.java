package backend.datn.dto.response;


import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
public class EmployeeRespone implements Serializable {
    Integer id;
    String employeeCode;
    RoleRespone role;
    String fullname;
    String username;
    String password;
    String email;
    String phone;
    String photo;
    Integer status;
    Instant createDate;
    Instant updateDate;
    Boolean forgetPassword;
    Boolean gender;
}