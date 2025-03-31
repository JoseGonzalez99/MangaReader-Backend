package com.hotbox.jaitymangareader.content.volume.repository;

import com.hotbox.jaitymangareader.content.volume.entity.Volume;
import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VolumeRepository extends JpaRepository<Volume, UUID> {
    List<Volume> findByManga(Manga manga);
}
