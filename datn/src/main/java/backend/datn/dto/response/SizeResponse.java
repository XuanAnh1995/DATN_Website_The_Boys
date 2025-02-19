package backend.datn.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link backend.datn_hn37.entities.Size}
 */
@Data
@Builder
public class SizeResponse implements Serializable {
    private final Integer id;
    private final String sizeName;
    private final Boolean status;
}