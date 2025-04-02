package com.hotbox.jaitymangareader.content.page.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PageCreateRequest(

        @Min(1)
        int pageNumber,

        @NotBlank
        String imageUrl
) {
}
