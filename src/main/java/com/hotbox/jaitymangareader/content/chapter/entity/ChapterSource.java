package com.hotbox.jaitymangareader.content.chapter.entity;

import com.hotbox.jaitymangareader.content.page.entity.Page;
import com.hotbox.jaitymangareader.content.provider.entity.Provider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "chapter_sources")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterSource {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(nullable = false, length = 10)
    private String languageCode;

    private boolean isActive;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "chapterSource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Page> pages;
}
