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


    @Builder.Default
    private int chaptersCount = 0;

    @Builder.Default
    private int volumesCount = 0;

    private String faviconUrl;
    private String coverUrl;

    private Double rating;

    private Instant createdAt;
    private Instant updatedAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Volume> volumes;
}
