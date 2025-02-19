package backend.datn.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link backend.datn_hn37.entities.Category}
 */
@Data
@Builder
public class CategoryResponse implements Serializable {
    private final Integer id;
    private final String categoryName;
    private final Boolean status;
}