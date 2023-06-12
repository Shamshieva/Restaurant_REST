package manas.dto.menuItem.response;

import java.math.BigDecimal;


public record MenuItemResponse(
        Long id,
        String name,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian
) {
}
