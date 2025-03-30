package com.hotbox.jaitymangareader.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {
    private String theme; // "light" o "dark"
    private String readingDirection; // "ltr" o "rtl"
    private String defaultProvider; // "mangadex", "manganato", etc.
}
