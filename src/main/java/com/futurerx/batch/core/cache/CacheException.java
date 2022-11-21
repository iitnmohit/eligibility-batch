package com.futurerx.batch.core.cache;

import com.futurerx.batch.core.exception.BatchException;
import org.springframework.lang.NonNull;

public class CacheException extends BatchException {
  public CacheException(@NonNull String message) {
    super(message);
  }
}
