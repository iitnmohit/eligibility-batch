package com.futurerx.batch.core.service;

import com.futurerx.batch.core.constant.BatchType;
import org.springframework.lang.NonNull;

@FunctionalInterface
public interface IBatchIdentity {

  @NonNull
  BatchType getBatchType();
}
