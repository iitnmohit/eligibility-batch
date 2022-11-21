package com.futurerx.batch.core.exception;

import org.springframework.lang.NonNull;

public class BatchException extends RuntimeException {
  public BatchException(@NonNull String message) {
    super(message);
  }
}
