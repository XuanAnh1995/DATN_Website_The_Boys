package backend.datn.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link backend.datn_hn37.entities.Collar}
 */
@Data
@Builder
public class CollarResponse implements Serializable {
    private final Integer id;
    private final String collarName;
    private final Boolean status;
}