package manas.dto.user.response;

import lombok.Builder;

@Builder
public record UserTokenResponse(
        String login,
        String token
) {
}
