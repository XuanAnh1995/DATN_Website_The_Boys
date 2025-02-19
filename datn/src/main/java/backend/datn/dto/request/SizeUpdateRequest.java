package backend.datn.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SizeUpdateRequest {
    private Integer id;
    private String name;
    private Boolean status;
}
