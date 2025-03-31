package com.hotbox.jaitymangareader.content.provider.controller;

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

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody @Valid Provider provider,
            HttpServletRequest request) {
        Provider saved = providerService.create(provider);
        return ResponseUtil.created(saved, "Proveedor registrado correctamente", request);
    }

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(defaultValue = "true") boolean activeOnly,
            HttpServletRequest request) {
        List<Provider> list = providerService.getAll(activeOnly);
        return ResponseUtil.success(list, "Proveedores obtenidos", request);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> toggle(
            @PathVariable UUID id,
            @RequestParam boolean enable,
            HttpServletRequest request) {
        Provider updated = providerService.toggleStatus(id, enable);
        return ResponseUtil.success(updated, "Estado actualizado", request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {
        providerService.delete(id);
        return ResponseUtil.noContent("Proveedor eliminado", request);
    }
}
