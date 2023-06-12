package manas.dto.menuItem.request;

import java.math.BigDecimal;


public record MenuItemRequest(
        String name,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian
) {
}
