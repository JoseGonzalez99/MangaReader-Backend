package com.hotbox.jaitymangareader.content.provider.controller;

import com.hotbox.jaitymangareader.content.provider.dto.ProviderCreateRequest;
import com.hotbox.jaitymangareader.content.provider.dto.ProviderUpdateRequest;
import com.hotbox.jaitymangareader.content.provider.dto.ProviderView;
import com.hotbox.jaitymangareader.content.provider.entity.Provider;
import com.hotbox.jaitymangareader.content.provider.service.ProviderService;
import com.hotbox.jaitymangareader.core.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/providers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        List<Provider> providers = providerService.getAll(false);
        List<ProviderView> views = providers.stream()
                .map(ProviderView::from)
                .toList();
        return ResponseUtil.success(views, "Proveedores cargados correctamente", request);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody @Valid ProviderCreateRequest request,
            HttpServletRequest http) {
        Provider provider = providerService.create(request);
        ProviderView view = ProviderView.from(provider);
        return ResponseUtil.created(view, "Proveedor creado", http);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody @Valid ProviderUpdateRequest request,
            HttpServletRequest http) {

        Provider updated = providerService.update(id, request);
        ProviderView view = ProviderView.from(updated);
        return ResponseUtil.success(view, "Proveedor actualizado", http);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> toggle(
            @PathVariable UUID id,
            @RequestParam boolean enable,
            HttpServletRequest request) {

        providerService.toggleStatus(id, enable);
        return ResponseUtil.noContent("Estado del proveedor actualizado", request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {

        providerService.delete(id);
        return ResponseUtil.noContent("Proveedor eliminado", request);
    }
}
