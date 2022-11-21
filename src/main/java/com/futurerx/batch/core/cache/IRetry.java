package com.futurerx.batch.core.cache;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Consumer;

import static com.futurerx.batch.core.cache.IRetryProperty.*;

@FunctionalInterface
public interface IRetry {

  @Nullable
  IRetryProperty withRetryProperty();

  @NonNull
  default Duration getRetryMaxBackoff() {
    return Duration.ofMillis(
        Optional.ofNullable(withRetryProperty())
            .map(IRetryProperty::getRetryMaxBackoffMillis)
            .orElse(DEFAULT_RETRY_MAX_BACKOFF_MILLIS));
  }

  @NonNull
  default Duration getRetryMinBackoff() {
    return Duration.ofMillis(
        Optional.ofNullable(withRetryProperty())
            .map(IRetryProperty::getRetryMinBackoffMillis)
            .orElse(DEFAULT_RETRY_MIN_BACKOFF_MILLIS));
  }

  @NonNull
  default long getRetryMaxAttempts() {
    return Optional.ofNullable(withRetryProperty())
        .map(IRetryProperty::getRetryMaxAttempts)
        .orElse(DEFAULT_RETRY_MAX_ATTEMPTS);
  }

  @NonNull
  default <T> Mono<T> addRetry(
      @NonNull Mono<T> mono, @NonNull Consumer<Retry.RetrySignal> doBeforeRetry) {
    return mono.retryWhen(
        Retry.backoff(getRetryMaxAttempts(), getRetryMinBackoff())
            .maxBackoff(getRetryMaxBackoff())
            .jitter(DEFAULT_RETRY_JITTER_FACTOR)
            .doBeforeRetry(doBeforeRetry));
  }
}
