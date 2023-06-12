package manas.dto.user.response;

import manas.entities.enums.Role;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String fullName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        Role role,
        int experience
) {
}
