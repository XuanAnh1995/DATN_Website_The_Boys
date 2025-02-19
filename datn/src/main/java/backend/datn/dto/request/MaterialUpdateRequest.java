package backend.datn.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialUpdateRequest implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 100)
    String materialName;
    @NotNull
    Boolean status;
}