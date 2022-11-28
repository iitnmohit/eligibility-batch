package com.futurerx.batch.core.sqs;

import com.google.common.base.Function;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SqsClientProvider implements Function<String, SqsClient> {

  private static final Map<String, SqsClient> amazonSqsClientRegionMap = new ConcurrentHashMap<>();

  @NonNull
  @Override
  public SqsClient apply(@NonNull String region) {
    return amazonSqsClientRegionMap.computeIfAbsent(
        region, key -> SqsClient.builder().region(Region.of(key)).build());
  }
}
