package com.futurerx.batch.core.cache;

import com.futurerx.batch.config.property.CacheProperty;
import com.futurerx.batch.core.exception.BatchException;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

import static java.util.Objects.isNull;

@Slf4j
public abstract class AbstractMonoCache<T, ID> implements ICacheReady, ICache, IRetry {

  private Cache<ID, Mono<T>> cache;
  private final Class<T> tType;

  @Autowired protected CacheProperty cacheProperty;

  protected AbstractMonoCache(@NonNull Class<T> tType) {
    this.tType = tType;
  }

  @NonNull
  protected abstract Mono<T> findFromRepository(@NonNull ID id);

  @NonNull
  public Mono<T> findFromCache(@Nullable ID id) {
    if (isNull(id)) {
      log.trace("data is not retrieved from cache {}, because id is null", tType);
      return Mono.empty();
    }
    try {
      if (getCacheEnabled()) {
        return getCache().get(id, () -> findFromRepositoryWithRetry(id).transform(this::addCache));
      } else {
        return findFromRepositoryWithRetry(id);
      }
    } catch (ExecutionException e) {
      var msg =
          String.format(
              "unexpected mono cache ERROR happened of type %s, while loading data for id %s : %s",
              tType, id, e.getMessage());
      log.error(msg);
      return Mono.error(new BatchException(msg));
    }
  }

  @NonNull
  private Mono<T> findFromRepositoryWithRetry(@NonNull ID id) {
    return findFromRepository(id)
        .transform(
            mono ->
                addRetry(
                    mono,
                    retrySignal ->
                        log.error(
                            "ERROR while fetching data from repository : {}, with id : {}, {}. Retry times : {}",
                            tType,
                            id,
                            retrySignal.failure().getMessage(),
                            retrySignal.totalRetriesInARow() + 1)))
        .doOnError(
            throwable ->
                log.error(
                    "ERROR while fetching data from repository : {}, with id : {}, {}, even after retrying {} times",
                    tType,
                    id,
                    throwable.getMessage(),
                    getRetryMaxAttempts()))
        .onErrorMap(throwable -> new BatchException(throwable.getMessage()));
  }

  @NonNull
  private Cache<ID, Mono<T>> getCache() {
    if (isNull(cache)) {
      cache = buildCache();
    }
    return cache;
  }

  @NonNull
  @Override
  public boolean prepareCache() {
    if (getCacheEnabled()) {
      log.trace("Invalidating Cache of type : {}", tType);
      getCache().invalidateAll();
      log.debug("Invalidated Cache of type : {}", tType);
    }
    return true;
  }
}
