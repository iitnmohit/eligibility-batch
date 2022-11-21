package com.futurerx.batch.core.hook.request.filter;

import com.futurerx.batch.core.constant.BatchType;
import com.futurerx.batch.core.hook.IPriority;
import com.futurerx.batch.core.model.AbstractBatchRequest;
import com.futurerx.batch.core.service.IBatchApply;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.function.Predicate;

import static com.futurerx.batch.common.util.Predicates.nonNegativeNumberPredicate;

public interface IRequestPreFilter<R extends AbstractBatchRequest>
    extends IBatchApply, IPriority, Predicate<R> {

  @NonNull
  @Override
  boolean test(@NonNull R request);

  @NonNull
  default IRequestPreFilter<R> and(@NonNull IRequestPreFilter<R> other) {
    var parent = this;
    return new IRequestPreFilter<>() {
      @NonNull
      @Override
      public boolean apply(@Nullable BatchType batchType) {
        return and(parent, other, batchType);
      }

      @NonNull
      @Override
      public boolean test(@NonNull R request) {
        return (nonNegativeNumberPredicate().test(parent.compareTo(other))
                ? ((Predicate<R>) parent).and(other)
                : ((Predicate<R>) other).and(parent))
            .test(request);
      }

      @NonNull
      @Override
      public int getPriority() {
        return andPriority(parent, other);
      }
    };
  }
}
