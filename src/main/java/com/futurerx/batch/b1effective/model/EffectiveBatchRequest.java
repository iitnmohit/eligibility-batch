package com.futurerx.batch.b1effective.model;

import com.futurerx.batch.core.model.AbstractBatchRequest;
import com.futurerx.batch.member.entity.MemberEligibilityEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EffectiveBatchRequest extends AbstractBatchRequest {

  private MemberEligibilityEntity memberEligibilityEntity;
  private LocalDate effectiveDate;

  @NonNull
  public EffectiveBatchRequest cloneFromMemberEligibilityEntity(
      @NonNull MemberEligibilityEntity memberEligibilityEntity) {
    return EffectiveBatchRequest.builder()
        .memberEligibilityEntity(memberEligibilityEntity)
        .effectiveDate(this.getEffectiveDate())
        .startTime(this.getStartTime())
        .endTime(this.getEndTime())
        .dataSourceType(this.getDataSourceType())
        .environmentType(this.getEnvironmentType())
        .build();
  }
}
