package com.hotbox.jaitymangareader.dto;

import com.hotbox.jaitymangareader.entity.Role;

public record AppUserDTO(
        String email,
        String password,
        Role role
) {}
