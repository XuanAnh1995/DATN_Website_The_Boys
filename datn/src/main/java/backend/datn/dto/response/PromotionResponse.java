package backend.datn.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link backend.datn_hn37.entities.Promotion}
 */
@Data
@Builder
public class PromotionResponse implements Serializable {
    private final Integer id;
    private final String promotionName;
    private final Integer promotionPercent;
    private final Instant startDate;
    private final Instant endDate;
    private final String description;
    private final Boolean status;
}