package com.futurerx.batch.b1effective.function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurerx.batch.b1effective.model.EffectiveBatchRequest;
import com.futurerx.batch.core.sqs.SqsEligibilityModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static com.futurerx.batch.common.Constants.SQS_TYPE_MEMBER_ELIGIBILITY;
import static com.futurerx.batch.common.Constants.STATUS_ACTIVE;

@Slf4j
@Component
public class EffectiveBatchRequestToSqsRequest implements Function<EffectiveBatchRequest, String> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @NonNull
  @Override
  public String apply(@NonNull EffectiveBatchRequest effectiveBatchRequest) {
    try {
      return objectMapper.writeValueAsString(
          SqsEligibilityModel.builder()
              .type(SQS_TYPE_MEMBER_ELIGIBILITY)
              .value(STATUS_ACTIVE)
              .idMemberInfo(effectiveBatchRequest.getMemberEligibilityEntity().getIdMemberInfo())
              .build());
    } catch (JsonProcessingException e) {
      log.error("error while parsing data : {}", e.getMessage());
      return "{}";
    }
  }
}
