package com.hotbox.jaitymangareader.content.provider.service;

import com.hotbox.jaitymangareader.content.provider.dto.ProviderCreateRequest;
import com.hotbox.jaitymangareader.content.provider.dto.ProviderUpdateRequest;
import com.hotbox.jaitymangareader.content.provider.entity.Provider;
import com.hotbox.jaitymangareader.content.provider.repository.ProviderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepo;

    public Provider create(ProviderCreateRequest request) {
        Provider provider = Provider.builder()
                .providerName(request.providerName())
                .providedLang(request.providedLang())
                .logoUrl(request.logoUrl())
                .isActive(true)
                .build();
        return providerRepo.save(provider);
    }

    public List<Provider> getAll(boolean onlyActive) {
        return onlyActive ? providerRepo.findByIsActiveTrue() : providerRepo.findAll();
    }

    public Provider getProviderById(UUID id ){
        return providerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
    }

    public void toggleStatus(UUID id, boolean enable) {
        Provider provider = providerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        provider.setActive(enable);
        providerRepo.save(provider);
    }

    public void delete(UUID id) {
        if (!providerRepo.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado");
        }
        providerRepo.deleteById(id);
    }

    public Provider update(UUID id, ProviderUpdateRequest request) {
        Provider existing = providerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        existing.setProviderName(request.providerName());
        existing.setProvidedLang(request.providedLang());
        existing.setLogoUrl(request.logoUrl());
        existing.setActive(request.isActive());

        return providerRepo.save(existing);
    }
}
