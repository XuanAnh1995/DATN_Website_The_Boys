package backend.datn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollarRequest  {
    @NotBlank(message = "Name is mandatory")
    private String collarName;

    @NotNull(message = "Status is mandatory")
    private Boolean status;
}