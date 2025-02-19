package backend.datn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SizeCreateRequest {
    @NotBlank(message = "Name is mandatory")
    String name;
    @NotNull
    Boolean status;
}