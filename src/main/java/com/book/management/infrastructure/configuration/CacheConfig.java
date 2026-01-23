package com.book.management.infrastructure.configuration;

import com.book.management.application.model.BookModel;
import com.book.management.infrastructure.constants.BookConstants;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public javax.cache.CacheManager jCacheManager() {
        // Get Ehcache JCache provider
        CachingProvider provider = Caching.getCachingProvider(EhcacheCachingProvider.class.getName());
        CacheManager cacheManager = provider.getCacheManager();

        // Define cache configuration
        MutableConfiguration<String, List<BookModel>> config =
                new MutableConfiguration<String, List<BookModel>>()
                        .setTypes(String.class, (Class<List<BookModel>>) (Class<?>) List.class)
                        .setStoreByValue(false) // store references (safe if immutable)
                        .setStatisticsEnabled(true)
                        .setExpiryPolicyFactory(
                                CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.HOURS, 1))
                        );

        // Create cache if not exists
        if (cacheManager.getCache(BookConstants.BOOK_LIST_CACHE, String.class, List.class) == null) {
            cacheManager.createCache(BookConstants.BOOK_LIST_CACHE, config);
        }

        return cacheManager;
    }

    @Bean
    public JCacheCacheManager cacheManager(javax.cache.CacheManager jCacheManager) {
        return new JCacheCacheManager(jCacheManager);
    }
}
