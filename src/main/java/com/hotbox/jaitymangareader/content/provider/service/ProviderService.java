package com.hotbox.jaitymangareader.content.provider.service;

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

    public Provider create(Provider provider) {
        return providerRepo.save(provider);
    }

    public List<Provider> getAll(boolean onlyActive) {
        return onlyActive ? providerRepo.findByIsActiveTrue() : providerRepo.findAll();
    }

    public Provider toggleStatus(UUID id, boolean enable) {
        Provider provider = providerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        provider.setActive(enable);
        return providerRepo.save(provider);
    }

    public void delete(UUID id) {
        if (!providerRepo.existsById(id)) {
            throw new EntityNotFoundException("Proveedor no encontrado");
        }
        providerRepo.deleteById(id);
    }
}
