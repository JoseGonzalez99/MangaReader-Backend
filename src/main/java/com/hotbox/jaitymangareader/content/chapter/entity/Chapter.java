package com.hotbox.jaitymangareader.content.chapter.entity;

import com.hotbox.jaitymangareader.content.volume.entity.Volume;
import com.hotbox.jaitymangareader.content.page.entity.Page;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chapters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volume_id", nullable = false)
    private Volume volume;

    private String chapterNumber;
    private String title;

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
}
