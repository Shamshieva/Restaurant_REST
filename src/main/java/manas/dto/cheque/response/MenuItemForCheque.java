package manas.dto.cheque.response;

import java.math.BigDecimal;


public record MenuItemForCheque(
        Long id,
        String name,
        BigDecimal price,
        Long amount
) {
}
