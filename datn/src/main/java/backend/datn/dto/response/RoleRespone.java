package backend.datn.dto.response;

import lombok.Builder;
import lombok.Data;


import java.io.Serializable;

@Data
@Builder
public class RoleRespone implements Serializable {
    Integer id;
    String name;
}