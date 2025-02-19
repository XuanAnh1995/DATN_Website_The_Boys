package backend.datn.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColorUpdateRequest {
    private Integer id;
    private String name;
    private Boolean status;
}
