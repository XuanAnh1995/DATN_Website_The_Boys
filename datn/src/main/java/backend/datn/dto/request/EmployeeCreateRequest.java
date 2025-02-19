package backend.datn.dto.request;

import backend.datn.entities.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreateRequest implements Serializable {
    @NotNull
    @Size(max = 50)
    String employeeCode;
    @NotNull
    Role role;
    @NotNull
    @Size(max = 255)
    String fullname;
    @NotNull
    @Size(max = 100)
    String username;
    @NotNull
    @Size(max = 255)
    String password;
    @NotNull
    @Size(max = 255)
    String email;
    @NotNull
    @Size(max = 20)
    String phone;
    @NotNull
    @Size(max = 250)
    String photo;
    @NotNull
    Integer status;
    @NotNull
    Instant createDate;
    @NotNull
    Instant updateDate;
    Boolean forgetPassword;
    @NotNull
    Boolean gender;
}