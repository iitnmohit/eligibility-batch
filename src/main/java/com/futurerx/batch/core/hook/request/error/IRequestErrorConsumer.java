package com.futurerx.batch.core.hook.request.error;

import com.futurerx.batch.core.constant.BatchType;
import com.futurerx.batch.core.exception.BatchException;
import com.futurerx.batch.core.hook.IPriority;
import com.futurerx.batch.core.model.AbstractBatchRequest;
import com.futurerx.batch.core.service.IBatchApply;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.function.BiConsumer;

import static com.futurerx.batch.common.util.Predicates.nonNegativeNumberPredicate;

public interface IRequestErrorConsumer<R extends AbstractBatchRequest>
    extends IBatchApply, IPriority, BiConsumer<BatchException, R> {

  @Override
  void accept(@NonNull BatchException batchException, @Nullable R r);

  @NonNull
  default IRequestErrorConsumer<R> and(@NonNull IRequestErrorConsumer<R> other) {
    var parent = this;
    return new IRequestErrorConsumer<>() {
      @NonNull
      @Override
      public boolean apply(@Nullable BatchType batchType) {
        return and(parent, other, batchType);
      }

      @Override
      public void accept(@NonNull BatchException batchException, @Nullable R r) {
        (nonNegativeNumberPredicate().test(parent.compareTo(other))
                ? parent.andThen(other)
                : other.andThen(parent))
            .accept(batchException, r);
      }

      @NonNull
      @Override
      public int getPriority() {
        return andPriority(parent, other);
      }
    };
  }
}
