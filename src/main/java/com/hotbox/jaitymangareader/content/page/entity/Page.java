package com.hotbox.jaitymangareader.content.page.entity;

import com.hotbox.jaitymangareader.content.chapter.entity.ChapterSource;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "pages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_source_id", nullable = false)
    private ChapterSource chapterSource;

    private int pageNumber;

    @Column(nullable = false)
    private String imageUrl;

    private Instant createdAt;
    private Instant updatedAt;

}
