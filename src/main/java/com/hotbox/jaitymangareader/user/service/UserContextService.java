package com.hotbox.jaitymangareader.user.service;

import com.hotbox.jaitymangareader.user.dto.ReadingStatus;
import com.hotbox.jaitymangareader.user.entity.ReadingEntry;
import com.hotbox.jaitymangareader.user.entity.UserContext;
import com.hotbox.jaitymangareader.user.repository.UserContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserContextService {

    private final UserContextRepository repository;

    public UserContext getByUserId(String userId) {
        return repository.findById(userId)
                .orElseGet(() -> {
                    UserContext ctx = UserContext.builder()
                            .userId(userId)
                            .lastActiveAt(Instant.now())
                            .build();
                    return repository.save(ctx);
                });
    }

    public UserContext update(String userId, UserContext updated) {
        updated.setUserId(userId);
        updated.setLastActiveAt(Instant.now());
        return repository.save(updated);
    }

    public void recordReadingProgress(String userId, String mangaId, String chapterId, int pageRead) {
        UserContext ctx = getByUserId(userId);

        ReadingEntry entry = ctx.getReadingHistory().stream()
                .filter(e -> e.getMangaId().equals(mangaId))
                .findFirst()
                .orElse(null);

        if (entry == null) {
            entry = ReadingEntry.builder()
                    .mangaId(mangaId)
                    .chapterId(chapterId)
                    .lastPageRead(pageRead)
                    .lastReadAt(Instant.now())
                    .status(ReadingStatus.IN_PROGRESS)
                    .build();
            ctx.getReadingHistory().add(entry);
        } else {
            entry.setChapterId(chapterId);
            entry.setLastPageRead(pageRead);
            entry.setLastReadAt(Instant.now());
            entry.setStatus(ReadingStatus.IN_PROGRESS);
        }

        ctx.setLastActiveAt(Instant.now());
        repository.save(ctx);
    }

}
