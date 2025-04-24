package com.hotbox.jaitymangareader.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(
        @NotBlank @Email String email,
        String fullName,
        String photoUrl
) {
}
