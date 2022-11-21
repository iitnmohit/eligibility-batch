package com.futurerx.batch.core.service;

import org.springframework.lang.NonNull;

@FunctionalInterface
public interface IValidator {

  @NonNull
  boolean isValid();
}
