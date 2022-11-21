package com.futurerx.batch.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;

import java.util.function.Predicate;

@UtilityClass
public final class Predicates {

  @NonNull
  public static <T extends Number> Predicate<T> nonNegativeNumberPredicate() {
    return t -> t.intValue() >= 0;
  }
}
