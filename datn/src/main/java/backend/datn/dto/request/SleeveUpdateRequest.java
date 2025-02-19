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

public class SleeveUpdateRequest implements Serializable {
    Integer id;
    @Size(message = "Vui lòng điền tên tay áo")
    String sleeveName;
    @NotNull
    Boolean status;
}