package manas.dto.user.request;

import lombok.Builder;

@Builder
public record UserRequest(
        String login,
        String password
) {
}
