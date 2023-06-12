package manas.dto.user.request;

public record AcceptOrRejectRequest(
        Long userId,
        boolean accept
) {
}
