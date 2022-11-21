package com.futurerx.batch.core.cache;

public interface IRetryProperty {
  Long DEFAULT_RETRY_MAX_ATTEMPTS = 5L;
  Long DEFAULT_RETRY_MIN_BACKOFF_MILLIS = 500L;
  Long DEFAULT_RETRY_MAX_BACKOFF_MILLIS = 10000L;
  Double DEFAULT_RETRY_JITTER_FACTOR = 0d;

  Long getRetryMaxAttempts();

  Long getRetryMinBackoffMillis();

  Long getRetryMaxBackoffMillis();
}
