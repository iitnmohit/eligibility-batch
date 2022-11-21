package com.futurerx.batch.core.cache;

import org.springframework.lang.NonNull;

@FunctionalInterface
public interface ICacheReady {

  @NonNull
  boolean prepareCache();
}
