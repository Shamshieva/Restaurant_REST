package manas.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import manas.entities.enums.Role;
import manas.validation.PasswordValid;
import manas.validation.PhoneNumberValid;

import java.time.LocalDate;

public record RegisterRequest(
        @NotNull(message = "first name should not be null")
        String firstName,
        @NotNull(message = "last name should not be null")
        String lastName,
        @NotNull(message = "date of birth should not be null")
        LocalDate dateOfBirth,
        @Email(message = "Write valid email!")
        @NotNull(message = "email should not be null")
        String email,
        @NotNull(message = "password should not be null")
        @PasswordValid(message = "Password length must be more than 4 symbols!")
        String password,
        @NotNull(message = "phone number should not be null")
//        @Pattern(regexp = "\\+996\\d{9}", message = "Phone number should start with +996 and consist of 13 characters!")
        @PhoneNumberValid(message = "Phone number should start with +996 and consist of 13 characters!")
        String phoneNumber,
        @NotNull(message = "role should not be null")
        Role role,
        @NotNull(message = "experience should not be null")
        int experience
) {
}
