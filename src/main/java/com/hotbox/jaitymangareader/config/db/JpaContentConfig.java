package com.hotbox.jaitymangareader.config.db;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.hotbox.jaitymangareader.content.manga.repository",
                "com.hotbox.jaitymangareader.content.volume.repository",
                "com.hotbox.jaitymangareader.content.chapter.repository",
                "com.hotbox.jaitymangareader.content.page.repository",
                "com.hotbox.jaitymangareader.content.provider.repository"
        }
)
@EntityScan(basePackages = {
        "com.hotbox.jaitymangareader.content.manga.entity",
        "com.hotbox.jaitymangareader.content.volume.entity",
        "com.hotbox.jaitymangareader.content.chapter.entity",
        "com.hotbox.jaitymangareader.content.page.entity",
        "com.hotbox.jaitymangareader.content.provider.entity"
})
public class JpaContentConfig {
}
