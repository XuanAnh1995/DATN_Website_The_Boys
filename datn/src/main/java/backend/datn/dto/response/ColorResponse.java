package backend.datn.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link backend.datn_hn37.entities.Color}
 */
@Data
@Builder
public class ColorResponse implements Serializable {
    private final Integer id;
    private final String colorName;
    private final Boolean status;
}