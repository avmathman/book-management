package com.book.management.application.service.components;

import com.book.management.application.mapper.BookMapper;
import com.book.management.application.model.BookModel;
import com.book.management.infrastructure.constants.BookConstants;
import com.book.management.infrastructure.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.util.List;

@Component
@Slf4j
public class CacheRefreshScheduler {

    private final Cache<String, List<BookModel>> booksCache;
    private final BookRepository repository;
    private final BookMapper mapper;

    public static final Object LOCK = new Object();

    public CacheRefreshScheduler(
            CacheManager cacheManager,
            BookRepository repository,
            BookMapper mapper
    ) {
        this.booksCache = cacheManager.getCache(
                BookConstants.BOOK_LIST_CACHE,
                String.class,
                (Class<List<BookModel>>) (Class<?>) List.class
        );
        this.repository = repository;
        this.mapper = mapper;
    }

    @Scheduled(fixedDelayString = "${management.cache.refresh-rate-ms}") // every 5 minutes
    public void refreshBookCache() {
        log.info("Refreshing book cache...");

        List<BookModel> bookModels = List.copyOf(mapper.entitiesToModels(repository.findAll()));
        booksCache.put(BookConstants.BOOK_LIST_CACHE, bookModels);
    }
}
