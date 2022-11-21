package com.futurerx.batch.core.service;

import com.futurerx.batch.core.constant.BatchType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@FunctionalInterface
public interface IBatchApply {

  @NonNull
  boolean apply(@Nullable BatchType batchType);

  @NonNull
  default <B extends IBatchApply> boolean and(
      @NonNull B batchApply1, @NonNull B batchApply2, @Nullable BatchType batchType) {
    return batchApply1.apply(batchType) && batchApply2.apply(batchType);
  }
}
