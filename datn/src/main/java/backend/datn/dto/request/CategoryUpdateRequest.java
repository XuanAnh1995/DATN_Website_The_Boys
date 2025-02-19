package backend.datn.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryUpdateRequest {
    private Integer id;
    private String name;
    private Boolean status;
}
