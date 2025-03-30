package com.hotbox.jaitymangareader.controller;

import com.hotbox.jaitymangareader.dto.AppUserDTO;
import com.hotbox.jaitymangareader.service.AppUserService;
import com.hotbox.jaitymangareader.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class AdminUserController {

    private final AppUserService service;

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        return ResponseUtil.success(service.getAll(), "Usuarios obtenidos correctamente", request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id, HttpServletRequest request) {
        return ResponseUtil.success(service.getById(id), "Usuario obtenido correctamente", request);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AppUserDTO dto, HttpServletRequest request) {
        return ResponseUtil.created(service.create(dto), "Usuario creado exitosamente", request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid AppUserDTO dto, HttpServletRequest request) {
        return ResponseUtil.success(service.update(id, dto), "Usuario actualizado correctamente", request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id, HttpServletRequest request) {
        service.delete(id);
        return ResponseUtil.noContent("Usuario eliminado correctamente", request);
    }
}
