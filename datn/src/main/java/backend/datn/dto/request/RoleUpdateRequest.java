package backend.datn.dto.request;

import lombok.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleUpdateRequest implements Serializable {
    String name;
}