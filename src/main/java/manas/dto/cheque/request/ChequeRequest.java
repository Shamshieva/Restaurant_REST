package manas.dto.cheque.request;

import java.util.List;

public record ChequeRequest(
        List<Long> mealsId
) {
}
