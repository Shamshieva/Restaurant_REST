package manas.dto.stopList.response;

import java.time.LocalDate;
public record StopListResponse(
        Long id,
        String reason,
        LocalDate date
) {
}
