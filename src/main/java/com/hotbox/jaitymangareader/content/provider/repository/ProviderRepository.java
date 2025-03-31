package com.hotbox.jaitymangareader.content.provider.repository;

import com.hotbox.jaitymangareader.content.provider.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProviderRepository extends JpaRepository<Provider, UUID> {
    List<Provider> findByIsActiveTrue();
    Optional<Provider> findByProviderNameIgnoreCase(String name);
}
