package com.hotbox.jaitymangareader.content.provider.dto;

import com.hotbox.jaitymangareader.content.provider.entity.Provider;

import java.util.UUID;

public record ProviderView(
        UUID id,
        String providerName,
        String providedLang,
        String logoUrl,
        boolean isActive
) {
    public static ProviderView from(Provider provider) {
        return new ProviderView(
                provider.getId(),
                provider.getProviderName(),
                provider.getProvidedLang(),
                provider.getLogoUrl(),
                provider.isActive()
        );
    }
}
