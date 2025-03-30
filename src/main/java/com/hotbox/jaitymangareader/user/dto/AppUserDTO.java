package com.hotbox.jaitymangareader.user.dto;

import com.hotbox.jaitymangareader.config.security.Role;

public record AppUserDTO(
        String email,
        String password,
        Role role
) {}
