package com.futurerx.batch.core.service;

import com.futurerx.batch.core.constant.BatchType;
import com.futurerx.batch.core.exception.BatchException;
import com.futurerx.batch.core.model.AbstractBatchRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

import static java.util.Optional.ofNullable;

public abstract class AbstractBatch<R extends AbstractBatchRequest> extends AbstractBatchBase<R>
    implements IBatchProcessor<R> {

  protected abstract Flux<R> expandRequest(@NonNull R request);

  @NonNull
  @Override
  public boolean apply(@Nullable BatchType batchType) {
    return getBatchType().equals(batchType);
  }

  @Override
  public void accept(@NonNull R r) {
    Flux.just(r)
        .flatMap(this::expandRequest)
        .doOnNext(request -> request.setStartTime(LocalDateTime.now()))
        .filter(
            request -> requestPreFilterOptional.map(filter -> filter.test(request)).orElse(true))
        .flatMap(this::requestProcessor)
        .doOnNext(request -> request.setEndTime(LocalDateTime.now()))
        .flatMap(this::requestPostProcessor)
        .onErrorContinue(BatchException.class, errorRequestBiConsumer());
  }

  @NonNull
  private Mono<R> requestProcessor(@NonNull R request) {
    return requestProcessorOptional
        .map(consumer -> consumer.apply(request))
        .orElseGet(() -> Mono.just(request));
  }

  @NonNull
  private Mono<R> requestPostProcessor(@NonNull R request) {
    return requestPostProcessorOptional
        .map(consumer -> consumer.apply(request))
        .orElseGet(() -> Mono.just(request));
  }

  @NonNull
  private BiConsumer<Throwable, Object> errorRequestBiConsumer() {
    return (throwable, request) ->
        requestErrorConsumerOptional.ifPresent(
            consumer ->
                consumer.accept(
                    (BatchException) throwable,
                    ofNullable(request)
                        .filter(AbstractBatchRequest.class::isInstance)
                        .map(o -> (R) o)
                        .orElse(null)));
  }
}
