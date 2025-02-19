package backend.datn.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCreateRequest implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 50)
    String customerCode;
    @Size(max = 255)
    String fullname;
    @NotNull
    @Size(max = 100)
    String username;
    @NotNull
    @Size(max = 255)
    String password;
    @Size(max = 255)
    String email;
    @Size(max = 20)
    String phone;
    @Size(max = 255)
    String address;
    Instant createDate;
    Instant updateDate;
    Boolean forgetPassword;

}