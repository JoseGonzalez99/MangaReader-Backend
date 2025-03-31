package com.hotbox.jaitymangareader.content.volume.entity;

import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import com.hotbox.jaitymangareader.content.chapter.entity.Chapter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "volumes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Volume {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id", nullable = false)
    private Manga manga;

    private int volumeNumber;
    private String title;
    private String coverUrl;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters;
}
