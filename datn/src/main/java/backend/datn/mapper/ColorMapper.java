package backend.datn.mapper;


import backend.datn.dto.response.ColorResponse;
import backend.datn.entities.Color;

public class ColorMapper {
    public static ColorResponse toColorResponse(Color color) {
        return ColorResponse.builder()
                .id(color.getId())
                .colorName(color.getColorName())
                .build();
    }
}
