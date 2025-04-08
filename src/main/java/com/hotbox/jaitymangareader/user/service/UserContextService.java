package com.hotbox.jaitymangareader.user.service;

import com.hotbox.jaitymangareader.content.manga.entity.Manga;
import com.hotbox.jaitymangareader.content.manga.repository.MangaRepository;
import com.hotbox.jaitymangareader.core.error.ApiException;
import com.hotbox.jaitymangareader.user.dto.ReadingEntryView;
import com.hotbox.jaitymangareader.user.entity.Preferences;
import com.hotbox.jaitymangareader.user.entity.ReadingEntry;
import com.hotbox.jaitymangareader.user.dto.ReadingStatus;
import com.hotbox.jaitymangareader.user.entity.UserContext;
import com.hotbox.jaitymangareader.user.repository.UserContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

import static com.hotbox.jaitymangareader.core.error.DomainErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserContextService {

    private final UserContextRepository repository;
    private final MangaRepository mangaRepository;


    public ReadingEntryView getLastReadDetailed(String userId) {
        UserContext ctx = getByUserId(userId);
        if (ctx.getLastRead() == null) {
            throw new ApiException(USER_CONTEXT_NO_LAST_READ);
        }

        Manga manga = mangaRepository.findById(UUID.fromString(ctx.getLastRead().getMangaId()))
                .orElseThrow(() -> new ApiException(USER_CONTEXT_NO_LAST_READ));

        return ReadingEntryView.from(ctx.getLastRead(), manga);
    }

    public List<ReadingEntryView> getReadingHistoryDetailed(String userId) {
        UserContext ctx = getByUserId(userId);

        List<ReadingEntryView> enriched = ctx.getReadingHistory().stream()
                .map(entry -> {
                    Manga manga = mangaRepository.findById(UUID.fromString(entry.getMangaId()))
                            .orElse(null);
                    return manga != null ? ReadingEntryView.from(entry, manga) : null;
                })
                .filter(Objects::nonNull)
                .toList();

        if (enriched.isEmpty()) {
            throw new ApiException(USER_CONTEXT_EMPTY_HISTORY);
        }

        return enriched;
    }
    public UserContext getByUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new ApiException(USER_CONTEXT_NOT_FOUND);
        }

        UserContext ctx = repository.findById(userId)
                .orElseGet(() -> {
                    UserContext newCtx = UserContext.builder()
                            .userId(userId)
                            .preferences(defaultPreferences())
                            .lastRead(null)
                            .readingHistory(new ArrayList<>())
                            .lastActiveAt(Instant.now())
                            .build();
                    return repository.save(newCtx);
                });

        if (ctx.getPreferences() == null) {
            ctx.setPreferences(defaultPreferences());
            repository.save(ctx);
        }

        if (ctx.getReadingHistory() == null) {
            ctx.setReadingHistory(new ArrayList<>());
        }

        return ctx;
    }

    public void update(String userId, UserContext context) {
        if (userId == null || context == null) {
            throw new ApiException(USER_CONTEXT_NOT_FOUND);
        }

        if (context.getPreferences() == null) {
            context.setPreferences(defaultPreferences());
        }

        context.setUserId(userId);
        repository.save(context);
    }

    public void recordReadingProgress(String userId, String mangaId, String chapterId, int pageRead) {
        if (userId == null || mangaId == null || chapterId == null || pageRead < 0) {
            throw new ApiException(INVALID_READING_INPUT);
        }

        UserContext ctx = getByUserId(userId);

        Optional<ReadingEntry> existingEntry = ctx.getReadingHistory().stream()
                .filter(e -> e.getMangaId().equals(mangaId))
                .findFirst();

        ReadingEntry entry;
        if (existingEntry.isPresent()) {
            entry = existingEntry.get();
            entry.setChapterId(chapterId);
            entry.setLastPageRead(pageRead);
            entry.setLastReadAt(Instant.now());
            entry.setStatus(ReadingStatus.IN_PROGRESS);
        } else {
            entry = ReadingEntry.builder()
                    .mangaId(mangaId)
                    .chapterId(chapterId)
                    .lastPageRead(pageRead)
                    .lastReadAt(Instant.now())
                    .status(ReadingStatus.IN_PROGRESS)
                    .build();
            ctx.getReadingHistory().add(entry);
        }

        ctx.setLastRead(entry);
        ctx.setLastActiveAt(Instant.now());

        repository.save(ctx);
    }

    private Preferences defaultPreferences() {
        return Preferences.builder()
                .theme("light")
                .readingDirection("ltr")
                .defaultProvider("")
                .build();
    }
}
