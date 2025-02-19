package backend.datn.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollarUpdateRequest {
    private Integer id;
    private String name;
    private Boolean status;
}
