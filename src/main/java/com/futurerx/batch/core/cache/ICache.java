package com.futurerx.batch.core.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.reactivestreams.Publisher;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

import static com.futurerx.batch.core.cache.ICacheProperty.*;
import static java.time.Duration.ofSeconds;

@FunctionalInterface
public interface ICache {

  @Nullable
  ICacheProperty withCacheProperty();

  @NonNull
  default <T> Mono<T> addCache(@NonNull Mono<T> mono) {
    return mono.cache(
        t -> ofSeconds(getExpireSeconds()), throwable -> Duration.ZERO, () -> Duration.ZERO);
  }

  @NonNull
  default long getExpireSeconds() {
    return Optional.ofNullable(withCacheProperty())
        .map(ICacheProperty::getExpireSecond)
        .orElse(DEFAULT_CACHE_EXPIRE_SECOND);
  }

  @NonNull
  default long getMaximumEntry() {
    return Optional.ofNullable(withCacheProperty())
        .map(ICacheProperty::getMaxEntry)
        .orElse(DEFAULT_CACHE_MAX_ENTRY);
  }

  @NonNull
  default boolean getCacheEnabled() {
    return Optional.ofNullable(withCacheProperty())
        .map(ICacheProperty::getEnabled)
        .orElse(DEFAULT_CACHE_ENABLED);
  }

  @NonNull
  default <T, ID, P extends Publisher<T>> Cache<ID, P> buildCache() {
    return CacheBuilder.newBuilder()
        .maximumSize(getMaximumEntry())
        .expireAfterAccess(ofSeconds(getExpireSeconds()))
        .build();
  }
}
