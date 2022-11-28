package com.futurerx.batch.core.sqs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SqsEligibilityModel {

  @JsonProperty("type")
  private String type;

  @JsonProperty("value")
  private String value;

  @JsonProperty("idMemberInfo")
  private Integer idMemberInfo;
}
