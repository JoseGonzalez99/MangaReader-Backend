package com.hotbox.jaitymangareader.user.service;

import com.hotbox.jaitymangareader.user.dto.AppUserDTO;
import com.hotbox.jaitymangareader.user.entity.AppUser;
import com.hotbox.jaitymangareader.config.security.Role;
import com.hotbox.jaitymangareader.user.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    // Listar todos los usuarios
    public List<AppUser> getAll() {
        return userRepo.findAll();
    }

    // Buscar por ID
    public AppUser getById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Buscar por email
    public Optional<AppUser> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    // Crear nuevo usuario
    public AppUser create(AppUserDTO dto) {
        if (userRepo.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        AppUser user = AppUser.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(dto.role() != null ? dto.role() : Role.CLIENT)
                .enabled(true)
                .createdAt(Instant.now())
                .build();

        return userRepo.save(user);
    }

    // Actualizar un usuario
    public AppUser update(String id, AppUserDTO dto) {
        AppUser existing = getById(id);

        if (!existing.getEmail().equals(dto.email())
                && userRepo.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email ya registrado por otro usuario");
        }

        existing.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.password()));
        }

        existing.setRole(dto.role());

        return userRepo.save(existing);
    }

    // Eliminar
    public void delete(String id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepo.deleteById(id);
    }

    public AppUser save(AppUser user) {
        return userRepo.save(user);
    }
}
