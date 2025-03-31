package com.hotbox.jaitymangareader.content.manga.entity;

import com.hotbox.jaitymangareader.content.volume.entity.Volume;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mangas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manga {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String title;
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int chaptersCount;
    private int volumesCount;

    private String faviconUrl;
    private String coverUrl;

    private Double rating;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Volume> volumes;
}
