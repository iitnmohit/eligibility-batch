package com.futurerx.batch.core.hook.request.processor;

import com.futurerx.batch.core.constant.BatchType;
import com.futurerx.batch.core.hook.IPriority;
import com.futurerx.batch.core.model.AbstractBatchRequest;
import com.futurerx.batch.core.service.IBatchApply;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.futurerx.batch.common.util.Predicates.nonNegativeNumberPredicate;

public interface IRequestPostProcessor<R extends AbstractBatchRequest>
    extends IBatchApply, IPriority, Function<R, Mono<R>> {

  @NonNull
  @Override
  Mono<R> apply(@NonNull R r);

  @NonNull
  default IRequestPostProcessor<R> and(@NonNull IRequestPostProcessor<R> other) {
    var parent = this;
    return new IRequestPostProcessor<>() {
      @NonNull
      @Override
      public boolean apply(@Nullable BatchType batchType) {
        return and(parent, other, batchType);
      }

      @NonNull
      @Override
      public Mono<R> apply(@NonNull R r) {
        return nonNegativeNumberPredicate().test(parent.compareTo(other))
            ? parent.apply(r).flatMap(other)
            : other.apply(r).flatMap(parent);
      }

      @NonNull
      @Override
      public int getPriority() {
        return andPriority(parent, other);
      }
    };
  }
}
