package com.book.management.infrastructure.configuration;

import com.book.management.application.model.BookModel;
import com.book.management.infrastructure.constants.UserConstants; // your constant
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager ehcacheManager() {
        CacheConfiguration<String, List<BookModel>> booksCacheConfig =
                CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                String.class,
                                (Class<List<BookModel>>) (Class<?>) List.class,
                                ResourcePoolsBuilder.heap(1)
                        )
                        .withExpiry(
                                ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(1))
                        )
                        .build();

        return CacheManagerBuilder
                .newCacheManagerBuilder()
                .withCache(UserConstants.BOOK_LIST_CACHE, booksCacheConfig)
                .build(true); // auto start
    }
}
