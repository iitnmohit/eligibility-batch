package com.futurerx.batch.b1effective.hook.processor;

import com.futurerx.batch.b1effective.function.EffectiveBatchRequestToSqsRequest;
import com.futurerx.batch.b1effective.hook.AbstractB1EffHook;
import com.futurerx.batch.b1effective.model.EffectiveBatchRequest;
import com.futurerx.batch.config.property.SqsProperty;
import com.futurerx.batch.core.hook.request.processor.IRequestProcessor;
import com.futurerx.batch.core.sqs.SqsSendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class B1ETriggerSQSRequestProcessor extends AbstractB1EffHook
    implements IRequestProcessor<EffectiveBatchRequest> {

  public static final int PRIORITY = 1000;

  @Autowired private SqsProperty sqsProperty;
  @Autowired private SqsSendMessage sqsSendMessage;
  @Autowired private EffectiveBatchRequestToSqsRequest effectiveBatchRequestToSqsRequest;

  @Override
  public int getPriority() {
    return PRIORITY;
  }

  @NonNull
  @Override
  public Mono<EffectiveBatchRequest> apply(@NonNull EffectiveBatchRequest effectiveBatchRequest) {
    return Mono.just(effectiveBatchRequest)
        .map(effectiveBatchRequestToSqsRequest)
        .doOnNext(msg -> sqsSendMessage.accept(sqsProperty.getEffectiveBatch(), msg))
        .map(o -> effectiveBatchRequest)
        .defaultIfEmpty(effectiveBatchRequest);
  }
}
