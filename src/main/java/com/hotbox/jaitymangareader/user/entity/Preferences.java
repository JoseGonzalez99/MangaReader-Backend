package com.hotbox.jaitymangareader.user.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Preferences {

    private String theme; // "light" o "dark"
    private String readingDirection; // "ltr" o "rtl"
    private String defaultProvider;
}
