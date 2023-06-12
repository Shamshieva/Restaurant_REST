package manas.dto.stopList.request;

import java.time.LocalDate;

public record StopListRequest(
        String reason,
        LocalDate date
) {
}
