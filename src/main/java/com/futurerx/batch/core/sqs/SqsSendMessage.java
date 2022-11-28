package com.futurerx.batch.core.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.function.BiConsumer;

@Component
public class SqsSendMessage implements BiConsumer<ISqsProperty, String> {

  @Autowired private SqsClientProvider sqsClientProvider;

  @Override
  public void accept(@NonNull ISqsProperty sqsProperty, @NonNull String message) {
    sqsClientProvider
        .apply(sqsProperty.getQueueRegion())
        .sendMessage(
            SendMessageRequest.builder()
                .queueUrl(sqsProperty.getQueueName())
                .messageBody(message)
                .build());
  }
}
