package com.hotbox.jaitymangareader.content.volume.controller;

import com.hotbox.jaitymangareader.content.volume.dto.VolumeCreateRequest;
import com.hotbox.jaitymangareader.content.volume.dto.VolumePublicView;
import com.hotbox.jaitymangareader.content.volume.dto.VolumeUpdateRequest;
import com.hotbox.jaitymangareader.content.volume.entity.Volume;
import com.hotbox.jaitymangareader.content.volume.service.VolumeService;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VolumeController {

    private final VolumeService volumeService;

    @GetMapping("/mangas/{mangaId}/volumes")
    public ResponseEntity<?> getVolumes(
            @PathVariable UUID mangaId,
            HttpServletRequest request) {

        List<Volume> volumes = volumeService.getByManga(mangaId);
        List<VolumePublicView> views = volumes.stream()
                .map(VolumePublicView::from)
                .toList();
        return ResponseUtil.success(views, "Volúmenes encontrados", request);
    }

    @GetMapping("/volumes/{id}")
    public ResponseEntity<?> getVolume(
            @PathVariable UUID id,
            HttpServletRequest request) {

        VolumePublicView view = VolumePublicView.from(volumeService.getById(id));
        return ResponseUtil.success(view, "Volumen encontrado", request);
    }

    @PostMapping("/admin/mangas/{mangaId}/volumes")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> create(
            @PathVariable UUID mangaId,
            @RequestBody @Valid VolumeCreateRequest req,
            HttpServletRequest request) {

        Volume created = volumeService.create(mangaId, req);
        VolumePublicView view =VolumePublicView.from(created);
        return ResponseUtil.created(view, "Volumen creado correctamente", request);
    }

    @PutMapping("/admin/volumes/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody @Valid VolumeUpdateRequest req,
            HttpServletRequest request) {

        Volume updated = volumeService.update(id, req);
        VolumePublicView view =VolumePublicView.from(updated);

        return ResponseUtil.success(view, "Volumen actualizado correctamente", request);
    }

    @DeleteMapping("/admin/volumes/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {

        volumeService.delete(id);
        return ResponseUtil.noContent("Volumen eliminado", request);
    }
}
