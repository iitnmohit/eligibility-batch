package com.futurerx.batch.core.service;

import com.futurerx.batch.core.constant.BatchType;
import com.futurerx.batch.core.hook.IPriority;
import com.futurerx.batch.core.hook.request.error.IRequestErrorConsumer;
import com.futurerx.batch.core.hook.request.filter.IRequestPreFilter;
import com.futurerx.batch.core.hook.request.processor.IRequestPostProcessor;
import com.futurerx.batch.core.hook.request.processor.IRequestProcessor;
import com.futurerx.batch.core.model.AbstractBatchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import static java.util.Comparator.reverseOrder;

public abstract class AbstractBatchBase<R extends AbstractBatchRequest> implements IBatchIdentity {

  @Autowired(required = false)
  private List<IRequestPreFilter<R>> requestPreFilterList;

  @Autowired(required = false)
  private List<IRequestProcessor<R>> requestProcessorList;

  @Autowired(required = false)
  private List<IRequestPostProcessor<R>> requestPostProcessorList;

  @Autowired(required = false)
  private List<IRequestErrorConsumer<R>> requestErrorConsumerList;

  protected Optional<IRequestPreFilter<R>> requestPreFilterOptional;

  protected Optional<IRequestProcessor<R>> requestProcessorOptional;

  protected Optional<IRequestPostProcessor<R>> requestPostProcessorOptional;

  protected Optional<IRequestErrorConsumer<R>> requestErrorConsumerOptional;

  @PostConstruct
  private void setUp() {
    requestPreFilterOptional =
        reduceFromList(requestPreFilterList, getBatchType(), IRequestPreFilter::and);
    requestProcessorOptional =
        reduceFromList(requestProcessorList, getBatchType(), IRequestProcessor::and);
    requestPostProcessorOptional =
        reduceFromList(requestPostProcessorList, getBatchType(), IRequestPostProcessor::and);
    requestErrorConsumerOptional =
        reduceFromList(requestErrorConsumerList, getBatchType(), IRequestErrorConsumer::and);
  }

  @NonNull
  private <T extends IBatchApply & IPriority> Optional<T> reduceFromList(
      @Nullable List<T> tList,
      @Nullable BatchType batchType,
      @NonNull BinaryOperator<T> accumulator) {
    return Optional.ofNullable(tList).stream()
        .flatMap(Collection::stream)
        .filter(t -> t.apply(batchType))
        .sorted(reverseOrder())
        .reduce(accumulator);
  }
}
