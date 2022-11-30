package com.futurerx.batch.b2termination.hook.processor;

import com.futurerx.batch.b2termination.function.TerminatedBatchRequestToSqsRequest;
import com.futurerx.batch.b2termination.hook.AbstractB2TerHook;
import com.futurerx.batch.b2termination.model.TerminatedBatchRequest;
import com.futurerx.batch.config.property.SqsProperty;
import com.futurerx.batch.core.hook.request.processor.IRequestProcessor;
import com.futurerx.batch.core.sqs.SqsSendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class B2TSendSQSRequestProcessor extends AbstractB2TerHook
    implements IRequestProcessor<TerminatedBatchRequest> {

  public static final int PRIORITY = 1000;

  @Autowired private SqsProperty sqsProperty;
  @Autowired private SqsSendMessage sqsSendMessage;
  @Autowired private TerminatedBatchRequestToSqsRequest terminatedBatchRequestToSqsRequest;

  @NonNull
  @Override
  public int getPriority() {
    return PRIORITY;
  }

  @NonNull
  @Override
  public Mono<TerminatedBatchRequest> apply(
      @NonNull TerminatedBatchRequest terminatedBatchRequest) {
    return Mono.just(terminatedBatchRequest)
        .map(terminatedBatchRequestToSqsRequest)
        .doOnNext(msg -> sqsSendMessage.accept(sqsProperty.getTerminationBatch(), msg))
        .doOnNext(s -> terminatedBatchRequest.getCounter().incrementAndGet())
        .map(o -> terminatedBatchRequest)
        .defaultIfEmpty(terminatedBatchRequest);
  }
}
