package backend.datn.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColorRequest  {
    @NotNull
    String colorName;
    @NotNull
    Boolean status;
}