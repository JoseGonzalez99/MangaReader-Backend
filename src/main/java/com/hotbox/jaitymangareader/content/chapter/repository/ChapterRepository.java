package com.hotbox.jaitymangareader.content.chapter.repository;

import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import com.hotbox.jaitymangareader.content.volume.entity.Volume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.UUID;

public interface ChapterRepository extends JpaRepository<Chapter, UUID> {
    List<Chapter> findByVolume(Volume volume);

    // ✅ Capítulos de un manga específico por ID
    @Query("SELECT c FROM Chapter c " +
            "JOIN c.volume v " +
            "JOIN v.manga m " +
            "WHERE m.id = :mangaId")
    List<Chapter> findAllByMangaId(UUID mangaId);

    // ✅ Capítulos por manga + idioma + proveedor
    @Query("SELECT DISTINCT c FROM Chapter c " +
            "JOIN c.volume v " +
            "JOIN v.manga m " +
            "JOIN c.sources s " +
            "JOIN s.provider p " +
            "WHERE m.id = :mangaId " +
            "AND s.languageCode = :languageCode " +
            "AND p.id = :providerId")
    List<Chapter> findByMangaIdAndLanguageAndProvider(UUID mangaId, String languageCode, UUID providerId);

    // ✅ Opcional: capítulos por manga e idioma solamente
    @Query("SELECT DISTINCT c FROM Chapter c " +
            "JOIN c.volume v " +
            "JOIN v.manga m " +
            "JOIN c.sources s " +
            "WHERE m.id = :mangaId " +
            "AND s.languageCode = :languageCode")
    List<Chapter> findByMangaIdAndLanguage(UUID mangaId, String languageCode);


}
